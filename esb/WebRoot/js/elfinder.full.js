(function($) {

	/**
	 * @class  File manager (main controller)
	 * @author dio dio@std42.ru
	 **/
	elFinder = function(el, o) {
		var self = this, id;
		
		this.log = function(m) {
			window.console && window.console.log && window.console.log(m);
		}
		/**
		 * Object. File manager configuration
		 **/			
		this.options.width = $(document).width();
		this.options.height = $(document).height()-70;
		this.options = $.extend({}, this.options, o||{});
		
		if (!this.options.url) {
			alert('Invalid configuration! You have to set URL option.');
			return;
		}
		
		/**
		 * String. element id, create random if not set;
		 **/
		this.id = '';
		if ((id = $(el).attr('id'))) {
			this.id = id;
		} else {
			// this.id = 'el-finder-'+Math.random().toString().substring(2);
		}
		
		/**
		 * String. Version number;
		 **/
		this.version  = '1.2';
		/**
		 * String. jQuery version;
		 **/
		this.jquery = $.fn.jquery.split('.').join('');

		/**
		 * Object. Current Working Dir info
		 **/
		this.cwd      = {};
		/**
		 * Object. Current Dir Content. Files/folders info
		 **/
		this.cdc      = {};
		/**
		 * Object. Buffer for copied files
		 **/
		this.buffer   = {};
		/**
		 * Array. Selected files IDs
		 **/
		this.selected = [];
		/**
		 * Array. Folder navigation history
		 **/
		this.history  = [];
		/**
		 * Boolean. Enable/disable actions
		 **/
		this.locked   = false;
		/**
		 * Number. Max z-index on page + 1, need for contextmenu and quicklook
		 **/
		this.zIndex = 2;
		/**
		 * DOMElement. jQueryUI dialog
		 **/
		this.dialog = null;
		/**
		 * DOMElement. For docked mode - place where fm is docked
		 **/
		this.anchor = this.options.docked ? $('<div/>').hide().insertBefore(el) : null;
		/**
		 * Object. Some options get from server
		 **/
		this.params = { dotFiles : false, arc : '', uplMaxSize : '' };
		this.vCookie = 'el-finder-view-'+this.id;
		this.pCookie = 'el-finder-places-'+this.id;
		this.lCookie = 'el-finder-last-'+this.id;
		this.navCookie = 'el-finder-lastnav-'+this.id;
		this.userCookie = 'el-finder-lastuser-'+this.id;
		/**
		 * Object. View. After init we can accessel as this.view.win
		 **/
		this.view = new this.view(this, el);
		/**
		 * Object. User Iterface. Controller for commands/buttons/contextmenu
		 **/
		this.ui = new this.ui(this);
		/**
		 * Object. Set/update events
		 **/
		this.eventsManager = new this.eventsManager(this);
		/**
		 * Object. Quick Look like in MacOS X :)
		 **/
		this.quickLook = new this.quickLook(this);
		
		/**
		 * Set/get cookie value
		 *
		 * @param  String  name  cookie name
		 * @param  String  value cookie value, null to unset
		 **/
		this.cookie = function(name, value) {			
			if (typeof value == 'undefined') {
				if (document.cookie && document.cookie != '') {
					var i, c = document.cookie.split(';');
					name += '=';
					for (i=0; i<c.length; i++) {
						c[i] = $.trim(c[i]);
						if (c[i].substring(0, name.length) == name) {
							return decodeURIComponent(c[i].substring(name.length));
						}
					}
				}
				return '';
			} else {
				
				var d, o = $.extend({}, this.options.cookie);
				if (value===null) {
					value = '';
					o.expires = -1;
				}
				if (typeof(o.expires) == 'number') {
					d = new Date();
					d.setTime(d.getTime()+(o.expires * 24 * 60 * 60 * 1000));
					o.expires = d;
				}
				document.cookie = name+'='+encodeURIComponent(value)+'; expires='+o.expires.toUTCString()+(o.path ? '; path='+o.path : '')+(o.domain ? '; domain='+o.domain : '')+(o.secure ? '; secure' : '');
			}
		}

		/**
		 * Set/unset this.locked flag
		 *
		 * @param  Boolean  state
		 **/
		this.lock = function(l) {
			this.view.spinner((this.locked = l||false));
			this.eventsManager.lock = this.locked;
		}

		/**
		 * Set/unset lock for keyboard shortcuts
		 *
		 * @param  Boolean  state
		 **/
		this.lockShortcuts = function(l) {
			this.eventsManager.lock = !!l;
		}
		
		/**
		 * Set file manager view type (list|icons)
		 *
		 * @param  String  v  view name
		 **/
		this.setView = function(v) {
			if (v == 'list' || v == 'icons') {
				this.options.view = v;
				this.cookie(this.vCookie, v);
			}
		}
		
		/**
		 * make ajax request, show message on error, call callback on success
		 *
		 * @param  Object.  data for ajax request
		 * @param  Function  
		 * @param  Object   overrwrite some options 
		 */
		this.ajax = function(data, callback, options) {

			var opts = {
				url      : this.options.url,
				async    : true,
				type     : 'GET',
				data     : data,
				dataType : 'json',
				cache    : false,
				lock     : true,
				force    : true,
				silent   : false
			}
			if (typeof(options) == 'object') {
				opts = $.extend({}, opts, options);
			}
			if (!opts.silent) {
				opts.error = self.view.fatal;
			}
			opts.success = function(data) {
				opts.lock && self.lock();
				if (data) {
					data.debug && self.log(data.debug);
					if (data.error) {
						!opts.silent && self.view.error(data.error, self.i18n(data.errorData));
						if (!opts.force) {
							return;
						}						
					}
					callback(data);

					delete data;
				}
				
			}
			opts.lock && this.lock(true);
			$.ajax(opts);
		}
		
		/**
		 * Load generated thumbnails in background
		 *
		 **/
		this.tmb = function() {
			this.ajax({cmd : 'tmb', current : self.cwd.hash,nav:self.cwd.nav}, function(data) {
				if (self.options.view == 'icons' && data.images && data.current == self.cwd.hash) {
					for (var i in data.images) {
						if (self.cdc[i]) {
							self.cdc[i].tmb = data.images[i];
							$('div[key="'+i+'"]>p', self.view.cwd).css('background', ' url("'+data.images[i]+'") 0 0 no-repeat');
						}
						
					}
					data.tmb && self.tmb();
				}
			}, {lock : false, silent : true});
		}
		
		/**
		 * Return folders in places IDs
		 *
		 * @return Array
		 **/
		this.getPlaces = function() {
			var pl = [], p = this.cookie(this.pCookie);
			if (p.length) {
				if (p.indexOf(':')!=-1) {
					pl = p.split(':');
				} else {
					pl.push(p);
				}
			}
			return pl;
		}
		
		/**
		 * Add new folder to places
		 *
		 * @param  String  Folder ID
		 * @return Boolean
		 **/
		this.addPlace = function(id) {
			var p = this.getPlaces();
			if ($.inArray(id, p) == -1) {
				p.push(id);
				this.savePlaces(p);
				return true;
			}
		}
		
		/**
		 * Remove folder from places
		 *
		 * @param  String  Folder ID
		 * @return Boolean
		 **/
		this.removePlace = function(id) {
			var p = this.getPlaces();
			if ($.inArray(id, p) != -1) {
				this.savePlaces($.map(p, function(o) { return o == id?null:o; }));
				return true;
			}
		}
		
		/**
		 * Save new places data in cookie
		 *
		 * @param  Array  Folders IDs
		 **/
		this.savePlaces = function(p) {
			this.cookie(this.pCookie, p.join(':'));
		}
		
		/**
		 * Update file manager content
		 *
		 * @param  Object  Data from server
		 **/
		this.reload = function(data) {
			var i;
			this.cwd = data.cwd;
			
			this.cdc = {};
//			if(typeof(data.cdc)== "undefined"){
//				return;
//			}
			for (i=0; i<data.cdc.length ; i++) {
				if (data.cdc[i].hash && data.cdc[i].name) {
					this.cdc[data.cdc[i].hash] = data.cdc[i];
					this.cwd.size += data.cdc[i].size;
				}
			}
			
			if (data.tree) {
//				this.view.renderLdwsnav();
//				this.view.renderNav(data.tree);
//				this.eventsManager.updateNav();
				this.view.renderLdwsnav(data.tree);
				this.eventsManager.updateldwsNav();
			}

			this.updateCwd();
			
			/* tell connector to generate thumbnails */
			if (data.tmb && !self.locked && self.options.view == 'icons') {
				self.tmb();
			}
			/* have to select some files */
			if (data.select && data.select.length) {
				var l = data.select.length;
				while (l--) {
					this.cdc[data.select[l]] && this.selectById(data.select[l]);
				}
			}
			this.lastDir(this.cwd.hash);
			this.lastNav(this.cwd.nav);
			
			if (this.options.autoReload>0) {
				this.iID && clearInterval(this.iID);
				this.iID = setInterval(function() {	!self.locked && self.ui.exec('reload'); }, this.options.autoReload*60000);
			}
		}
		
		/**
		 * Redraw current directory
		 *
		 */
		this.updateCwd = function() {
			this.lockShortcuts(true);
			this.selected = [];
			this.view.renderBrowse();
			this.view.renderCwd();
			this.eventsManager.updateBrowse();
			this.eventsManager.updateCwd();
			this.view.tree.find('a[key="'+this.cwd.phash+'"]').trigger('select');
			this.lockShortcuts();
		}
		
		/**
		 * Execute after files was dropped onto folder
		 *
		 * @param  Object  drop event
		 * @param  Object  drag helper object
		 * @param  String  target folder ID
		 */
		this.drop = function(e, ui, target) {
			if (ui.helper.find('[key="'+target+'"]').length) {
				return self.view.error('Unable to copy into itself');
			}
			var ids = [];
			ui.helper.find('div:not(.noaccess):has(>label):not(:has(em[class="readonly"],em[class=""]))').each(function() {
				ids.push($(this).hide().attr('key'));
			});
		
			if (!ui.helper.find('div:has(>label):visible').length) {
				ui.helper.hide();
			}
			if (ids.length) {
				self.setBuffer(ids, e.shiftKey?0:1, target);
				if (self.buffer.files) {
					/* some strange jquery ui bug (in list view) */
					setTimeout(function() {self.ui.exec('paste'); self.buffer = {}}, 500);
				}
			} else {
				$(this).removeClass('el-finder-droppable');
			}
		}
		
		/**
		 * Return selected files data
		 *
		 * @param  Number  if set, returns only element with this index or empty object 
		 * @return Array|Object
		 */
		this.getSelected = function(ndx) {
			var i, s = [];
			if (ndx>=0) {
				return this.cdc[this.selected[ndx]]||{};
			}
			for (i=0; i<this.selected.length; i++) {
				this.cdc[this.selected[i]] && s.push(this.cdc[this.selected[i]]);
			}
			return s;
		}
		
		this.select = function(el, reset) {
			reset && $('.ui-selected', self.view.cwd).removeClass('ui-selected');
			el.addClass('ui-selected');
			self.updateSelect();
		}

		this.selectById = function(id) {
			var el = $('[key="'+id+'"]', this.view.cwd);
			if (el.length) {
				this.select(el);
				this.checkSelectedPos();
			}
		}

		this.unselect = function(el) {
			el.removeClass('ui-selected');
			self.updateSelect();
		}

		this.toggleSelect = function(el) {
			el.toggleClass('ui-selected');
			this.updateSelect();
		}

		this.selectAll = function() {
			$('[key]', self.view.cwd).addClass('ui-selected')
			self.updateSelect();
		}

		this.unselectAll = function() {
			$('.ui-selected', self.view.cwd).removeClass('ui-selected');
			self.updateSelect();
		}

		this.updateSelect = function() {
			self.selected = [];
			$('.ui-selected', self.view.cwd).each(function() {
				self.selected.push($(this).attr('key'));
			});
			self.view.selectedInfo();
			self.ui.update();
			self.quickLook.update();
		}

		/**
		 * Scroll selected element in visible position
		 *
		 * @param  Boolean  check last or first selected element?
		 */
		this.checkSelectedPos = function(last) {
			var s = self.view.cwd.find('.ui-selected:'+(last ? 'last' : 'first')).eq(0),
				p = s.position(),
				h = s.outerHeight(),
				ph = self.view.cwd.height();
			if (p.top < 0) {
				self.view.cwd.scrollTop(p.top+self.view.cwd.scrollTop()-2);
			} else if (ph - p.top < h) {
				self.view.cwd.scrollTop(p.top+h-ph+self.view.cwd.scrollTop());
			}
		}

		/**
		 * Add files to clipboard buffer
		 *
		 * @param  Array   files IDs
		 * @param  Boolean copy or cut files?
		 * @param  String  destination folder ID
		 */
		this.setBuffer = function(files, cut, dst) {
			var i, id, f;
			this.buffer = {
				src   : this.cwd.hash,
				dst   : dst,
				files : [],
				names : [],
				mimes : [],
				cut   : cut||0
			};
			
			for (i=0; i<files.length; i++) {
				id = files[i]; 
				f = this.cdc[id];
				if (f && f.read && f.type != 'link') {
					this.buffer.files.push(f.hash);
					this.buffer.names.push(f.name);
					this.buffer.mimes.push(f.mime);
				}
			}
			
			if (!this.buffer.files.length) {
				this.buffer = {};
			}
		}
		this.clearBuffer = function(){
			this.buffer = {};
		}
		
		/**
		 * Return true if file name is acceptable
		 *
		 * @param  String  file/folder name
		 * @return Boolean
		 */
		this.isValidName = function(n) {
			if (!this.cwd.dotFiles && n.indexOf('.') == 0) {
				return false;
			}
			return n.match(/^[^\\\/\<\>:]+$/);
		}
		
		/**
		 * Return true if file with this name exists
		 *
		 * @param  String  file/folder name
		 * @return Boolean
		 */
		this.fileExists = function(n) {
			for (var i in this.cdc) {
				if (this.cdc[i].name == n) {					
					return i;				
				}
			}
			return false;
		}
		
		/**
		 * Return name for new file/folder
		 *
		 * @param  String  base name (i18n)
		 * @param  String  extension for file
		 * @return String
		 */
		this.uniqueName = function(n, ext) {
			n = self.i18n(n);
			var name = n, i = 0, ext = ext||'';

			if (!this.fileExists(name+ext)) {
				return name+ext;
			}

			while (i++<100) {
				if (!this.fileExists(name+i+ext)) {
					return name+i+ext;
				}
			}
			return name.replace('100', '')+Math.random()+ext;
		}

		/**
		 * Get/set last opened dir
		 *
		 * @param  String  dir hash
		 * @return String
		 */
		this.lastDir = function(dir) {
			if (this.options.rememberLastDir) {
				return dir ? this.cookie(this.lCookie, dir) : this.cookie(this.lCookie);
			}
		};
		this.lastNav = function(nav) {
			return nav ? this.cookie(this.navCookie, nav) : this.cookie(this.navCookie);			
		};	
		this.lastUser = function(user) {
			return user ? this.cookie(this.userCookie, user) : this.cookie(this.userCookie);			
		};
		/**
		 * Resize file manager
		 *
		 * @param  Number  width
		 * @param  Number  height
		 */
		function resize(w, h) {
//			w && self.view.win.width(w);
//			h && self.view.nav.add(self.view.cwd).height(h);	
			w && self.view.win.width(w);
			h && self.view.nav.height(h);
			h && self.view.cwd.height(h-95);
		}
		
		/**
		 * Resize file manager in dialog window while it resize
		 *
		 */
		function dialogResize() {
			resize(null, self.dialog.height()-self.view.tlb.parent().height()-($.browser.msie ? 47 : 32))
		}

		this.time = function() {
			return new Date().getMilliseconds();
		}

		/* here we init file manager */
		
		this.setView(this.cookie(this.vCookie));
		resize(self.options.width, self.options.height);

		/* dialog or docked mode */
		if (this.options.dialog || this.options.docked) {
			this.options.dialog = $.extend({width : 570, dialogClass : '', minWidth : 480, minHeight: 330}, this.options.dialog || {});
			this.options.dialog.open = function() {  
				setTimeout(function() {
					$('<input type="text" value="f"/>').appendTo(self.view.win).focus().select().remove()
				}, 200)
				
			}
			this.options.dialog.dialogClass += 'el-finder-dialog';
			this.options.dialog.resize = dialogResize;
			if (this.options.docked) {
				/* docked mode - create dialog and store size */
				this.options.dialog.close = function() { self.dock(); };
				
				this.view.win.data('size', {width : this.view.win.width(), height : this.view.nav.height()});
			} else {
				this.options.dialog.close = function() { 
					self.destroy();
				}
				this.dialog = $('<div/>').append(this.view.win).dialog(this.options.dialog);
			}
		}
		if(this.lastUser()){			
			if(this.options.user!=this.lastUser()){				
				this.lastDir(' ');	
			}
		}
		this.lastUser(this.options.user);
		
		this.ajax({ 
			cmd    : 'open', 
			target : this.lastDir()||'', 
			nav : this.lastNav()||'', 
			init   : true, 
			tree   : true 
			}, 
			function(data) {
				if (data.cwd) {
					self.eventsManager.init();
					self.reload(data);
					$.extend(self.params, data.params||{});

					$('*', document.body).each(function() {
						var z = parseInt($(this).css('z-index'));
						if (z >= self.zIndex) {
							self.zIndex = z+1;
						}
					});
					self.ui.init(data.disabled);
				}
				
		}, {force : true});		
		
		this.open = function() {
			this.dialog ? this.dialog.dialog('open') : this.view.win.show();
			this.eventsManager.lock = false;
		}
		
		this.close = function() {
			this.quickLook.hide();
			if (this.options.docked && this.view.win.attr('undocked')) {
				this.dock();
			} else {
				this.dialog ? this.dialog.dialog('close') : this.view.win.hide();
			}
			this.eventsManager.lock = true;
		}
		
		this.destroy = function() {

			this.eventsManager.lock = true;
			this.quickLook.hide();
			this.quickLook.win.remove();
			if (this.dialog) {
				this.dialog.dialog('destroy');
				this.view.win.parent().remove();
			} else {
				this.view.win.remove();
			}
			this.ui.menu.remove();
		}
		
		this.dock = function() {
			if (this.options.docked && this.view.win.attr('undocked')) {
				this.quickLook.hide();
				var s =this.view.win.data('size');
				this.view.win.insertAfter(this.anchor).removeAttr('undocked');
				resize(s.width, s.height);
				this.dialog.dialog('destroy');
				this.dialog = null;
			}
		}
		
		this.undock = function() {
			if (this.options.docked && !this.view.win.attr('undocked')) {
				this.quickLook.hide();
				this.dialog = $('<div/>').append(this.view.win.css('width', '100%').attr('undocked', true).show()).dialog(this.options.dialog);
				dialogResize();
			} 
		}
		//upload
		this.transport = {};
		this.transport.upload = $.proxy(this.uploads.xhr, this);
		this.upload = function (files,convertfile) {
		    return this.transport.upload(files, this,convertfile);
		};				
	}
	/**
	 * Prototype
	 * 
	 * @type  Object
	 * 
	 */
	elFinder.prototype = {
			notify : function(opts) {
				var type     = opts.type,
					msg      = '正在忙',
					ndialog  = this.ui.notify,
					notify   = ndialog.children('.elfinder-notify-'+type),
					ntpl     = '<div class="elfinder-notify elfinder-notify-{type}"><span class="elfinder-dialog-icon elfinder-dialog-icon-{type}"/><span class="elfinder-notify-msg">{msg}</span> <span class="elfinder-notify-cnt"/><div class="elfinder-notify-progressbar"><div class="elfinder-notify-progress"/></div></div>',
					delta    = opts.cnt,
					progress = opts.progress >= 0 && opts.progress <= 100 ? opts.progress : 0,
					cnt, total, prc;

				if (!type) {
					return this;
				}
				
				if (!notify.length) {
					notify = $(ntpl.replace(/\{type\}/g, type).replace(/\{msg\}/g, msg))
						.appendTo(ndialog)
						.data('cnt', 0);

					if (progress) {
						notify.data({progress : 0, total : 0});
					}
				}

				cnt = delta + parseInt(notify.data('cnt'));
				
				if (cnt > 0) {
					!opts.hideCnt && notify.children('.elfinder-notify-cnt').text('('+cnt+')');
					ndialog.is(':hidden') && ndialog.dialog('open');
					notify.data('cnt', cnt);
					
					if (progress < 100
					&& (total = notify.data('total')) >= 0
					&& (prc = notify.data('progress')) >= 0) {

						total    = delta + parseInt(notify.data('total'));
						prc      = progress + prc;
						progress = parseInt(prc/total);
						notify.data({progress : prc, total : total});
						
						ndialog.find('.elfinder-notify-progress')
							.animate({
								width : (progress < 100 ? progress : 100)+'%'
							}, 20);
					}
					
				} else {
					notify.remove();
					!ndialog.children().length && ndialog.dialog('close');
				}
				
				return this;
			},			
			uploads : {
				    // upload transport using iframe
				    iframe: function (data, fm) {
				        var self = fm ? fm : this,
				            input = data.input,
				            dfrd = $.Deferred()
				                .fail(function (error) {				                	
				                    error && self.error(error);
				                })
				                .done(function (data) {
				                    data.warning && self.error(data.warning);
				                    data.removed && self.remove(data);
				                    data.added && self.add(data);
				                    data.changed && self.change(data);
				                    self.trigger('upload', data);
				                    data.sync && self.sync();
				                }),
				            name = 'iframe-' + self.namespace + (++self.iframeCnt),
				            form = $('<form action="' + self.uploadURL + '" method="post" enctype="multipart/form-data" encoding="multipart/form-data" target="' + name + '" style="display:none"><input type="hidden" name="cmd" value="upload" /></form>'),
				            msie = this.UA.IE,
				            // clear timeouts, close notification dialog, remove form/iframe
				            onload = function () {
				                abortto && clearTimeout(abortto);
				                notifyto && clearTimeout(notifyto);
				                notify && self.notify({
				                        type: 'upload',
				                        cnt: -cnt
				                    });

				                setTimeout(function () {
				                        msie && $('<iframe src="javascript:false;"/>').appendTo(form);
				                        form.remove();
				                        iframe.remove();
				                    }, 100);
				            },
				            iframe = $('<iframe src="' + (msie ? 'javascript:false;' : 'about:blank') + '" name="' + name + '" style="position:absolute;left:-1000px;top:-1000px" />')
				                .bind('load', function () {
				                    iframe.unbind('load')
				                        .bind('load', function () {
				                            var data = self.parseUploadData(iframe.contents().text());

				                            onload();
				                            data.error ? dfrd.reject(data.error) : dfrd.resolve(data);
				                        });

				                    // notify dialog
				                    notifyto = setTimeout(function () {
				                            notify = true;
				                            self.notify({
				                                    type: 'upload',
				                                    cnt: cnt
				                                });
				                        }, self.options.notifyDelay);

				                    // emulate abort on timeout
				                    if (self.options.iframeTimeout > 0) {
				                        abortto = setTimeout(function () {
				                                onload();
				                                dfrd.reject([errors.connect, errors.timeout]);
				                            }, self.options.iframeTimeout);
				                    }

				                    form.submit();
				                }),
				            cnt, notify, notifyto, abortto

				            ;

				        if (input && $(input).is(':file') && $(input).val()) {
				            form.append(input);
				        } else {
				            return dfrd.reject();
				        }

				        cnt = input.files ? input.files.length : 1;

				        form.append('<input type="hidden" name="' + (self.newAPI ? 'target' : 'current') + '" value="' + self.cwd().hash + '"/>')
				            .append('<input type="hidden" name="html" value="1"/>')
				            .append($(input).attr('name', 'upload[]'));

				        $.each(self.options.onlyMimes || [], function (i, mime) {
				                form.append('<input type="hidden" name="mimes[]" value="' + mime + '"/>');
				            });

				        $.each(self.options.customData, function (key, val) {
				                form.append('<input type="hidden" name="' + key + '" value="' + val + '"/>');
				            });

				        form.appendTo('body');
				        iframe.appendTo('body');

				        return dfrd;
				    },
				    parseUploadData: function (text) {
				        var data;

				        if (!$.trim(text)) {
				            return {
				                error: ['errResponse', 'errDataEmpty']
				            };
				        }

				        try {
				            data = $.parseJSON(text);
				        } catch (e) {
				            return {
				                error: ['errResponse', 'errDataNotJSON']
				            }
				        }
				        return data;

				    },
				    // upload transport using XMLHttpRequest
				    xhr: function (data, fm,convertfile) {
				        var self = fm ? fm : this,
				            dfrd = $.Deferred()
				                .fail(function (error) {
				                    error && self.view.error(self.i18n(error));
				                })
				                .done(function (data) {
				                    data.warning && self.error(data.warning);
				                    data.removed && self.remove(data);
				                    data.added && self.add(data);
				                    data.changed && self.change(data);				                   
				                    self.ui.fm.reload(data);
				                })
				                .always(function () {
				                    notifyto && clearTimeout(notifyto);
				                    notify && self.notify({
				                            type: 'upload',
				                            cnt: -cnt,
				                            progress: 100 * cnt
				                        });
				                }),
				            xhr = new XMLHttpRequest(),
				            formData = new FormData(),
				            files = data.input ? data.input.files : data.files,
				            cnt = files.length,
				            loaded = 5,
				            notify = false,
				            startNotify = function () {
				                return setTimeout(function () {				                		
				                        notify = true;
				                        self.notify({
				                                type: 'upload',
				                                cnt: cnt,
				                                progress: loaded * cnt
				                            });
				                    }, self.options.notifyDelay);
				            },
				            notifyto;

				        if (!cnt) {
				            return dfrd.reject();
				        }

				        xhr.addEventListener('error', function () {
				                dfrd.reject('errConnect');
				            }, false);

				        xhr.addEventListener('abort', function () {
				                dfrd.reject(['errConnect', 'errAbort']);
				            }, false);

				        xhr.addEventListener('load', function () {
				                var status = xhr.status,
				                    data;

				                if (status > 500) {
				                    return dfrd.reject('errResponse');
				                }
				                if (status != 200) {
				                    return dfrd.reject('errConnect');
				                }
				                if (xhr.readyState != 4) {
				                    return dfrd.reject(['errConnect', 'errTimeout']); // am i right?
				                }
				                if (!xhr.responseText) {
				                    return dfrd.reject(['errResponse', 'errDataEmpty']);
				                }

				                data = self.uploads.parseUploadData(xhr.responseText);	
				                if(data.error){
				                	self.view.error(self.i18n(data.error));
				                }
				                data.error ? dfrd.reject(data.error) : dfrd.resolve(data);
				            }, false);

				        xhr.upload.addEventListener('progress', function (e) {				        		
				                var prev = loaded,
				                    curr;
				                if (e.lengthComputable) {

				                    curr = parseInt(e.loaded * 100 / e.total);

				                    // to avoid strange bug in safari (not in chrome) with drag&drop.
				                    // bug: macos finder opened in any folder,
				                    // reset safari cache (option+command+e), reload elfinder page,
				                    // drop file from finder
				                    // on first attempt request starts (progress callback called ones) but never ends.
				                    // any next drop - successfull.
				                    if (curr > 0 && !notifyto) {
				                    	self.lock(true);
//				                        notifyto = startNotify();
				                    }

				                    if (curr - prev > 4) {
//				                        loaded = curr;
//				                        notify && self.notify({
//				                                type: 'upload',
//				                                cnt: 0,
//				                                progress: (loaded - prev) * cnt
//				                            });
				                    }
				                    if(curr>=100){
				                    	self.lock(false);
				                    }
				                }
				            }, false);

				        
				        var uploadurl = self.options.url;
				        if(convertfile){
				        	uploadurl = uploadurl+"?force=true";
				        }
				        xhr.open('POST', uploadurl, true);
				        formData.append('cmd', 'upload');
				        formData.append('current', self.cwd.hash);
				        formData.append('masterdc', self.cwd.masterdc);
				        formData.append('ownerid', self.cwd.ownerid);
				        
				        $.each(files, function (i, file) {
				                formData.append('upload[]', file);
				            });

				        xhr.onreadystatechange = function () {
				            if (xhr.readyState == 4 && xhr.status == 0) {
				                // ff bug while send zero sized file
				                // for safari - send directory
				                dfrd.reject(['errConnect', 'errAbort']);
				            }
				        }
				        
				        xhr.send(formData);

				        return dfrd;
				    }				 
				}
	}
	/**
	 * Translate message into selected language
	 *
	 * @param  String  message in english
	 * @param  String  translated or original message
	 */
	elFinder.prototype.i18n = function(m) {
		return this.options.i18n[this.options.lang] && this.options.i18n[this.options.lang][m] ? this.options.i18n[this.options.lang][m] :  m;
	}
	
	/**
	 * Default config
	 *
	 */
	elFinder.prototype.options = {
		/* connector url. Required! */
		url            : '',
		/* interface language */
		lang           : 'en',
		/* additional css class for filemanager container */
		cssClass       : '',
		/* characters number to wrap file name in icons view. set to 0 to disable wrap */
		wrap           : 14,
		/* Name for places/favorites (i18n), set to '' to disable places */
		places         : 'Web Client',
		/* show places before navigation? */
		placesFirst    : true,
		/* callback to get file url (for wswing editors) */
		editorCallback : null,
		/* string to cut from file url begin before pass it to editorCallback. variants: '' - nothing to cut, 'root' - cut root url, 'http://...' - string if it exists in the beginig of url  */
		cutURL         : '',
		/* close elfinder after editorCallback */
		closeOnEditorCallback : true,
		/* i18 messages. not set manually! */
		i18n           : {},
		/* fm view (icons|list) */
		view           : 'list',
		/* width to overwrite css options */
		width          : '',
		/* height to overwrite css options. Attenion! this is heigt of navigation/cwd panels! not total fm height */
		height         : '',
		/* disable shortcuts exclude arrows/space */
		disableShortcuts : false,
		/* open last visited dir after reload page or close and open browser */
		rememberLastDir : true,
		/* cookie options */
		cookie         : {
			expires : 1,
			domain  : '',
			path    : '/',
			secure  : false
		},
		/* buttons on toolbar , 'edit'['info', 'resize'],*/
		toolbar        : [
			['back', 'reload'],
			['select', 'open'],
			['mkdir', 'mkfile', 'upload','download'],
			['paste', 'rm'],
			['rename'],			
			['icons', 'list']
		],
		/* contextmenu commands */
		contextmenu : {
			'cwd'   : ['reload', 'delim', 'mkdir', 'mkfile', 'upload', 'delim', 'paste', 'delim', 'info'],
			'file'  : ['select', 'open', 'delim', 'download', 'cut', 'rm', 'delim', 'rename', 'edit', 'resize', 'archive', 'extract', 'delim', 'info'],
			'group' : ['select', 'cut', 'rm', 'delim', 'archive', 'extract', 'delim', 'info']
		},
		/* jqueryUI dialog options */
		dialog : null,
		/* docked mode */
		docked : false,
		/* auto reload time (min) */
		autoReload : 0,
		/* set to true if you need to select several files at once from editorCallback */
		selectMultiple : false,
		notifyDelay : 500,
		user : ''
	}

	
	$.fn.elfinder = function(o) {
		
		return this.each(function() {
			
			var cmd = typeof(o) == 'string' ? o : '';
			if (!this.elfinder) {
				this.elfinder = new elFinder(this, typeof(o) == 'object' ? o : {})
			}
			
			switch(cmd) {
				case 'close':
				case 'hide':
					this.elfinder.close();
					break;
					
				case 'open':
				case 'show':
					this.elfinder.open();
					break;
				
				case 'dock':
					this.elfinder.dock();
					break;
					
				case 'undock':
					this.elfinder.undock();
					break;
					
				case'destroy':
					this.elfinder.destroy();
					break;
			}
			
		})
	}
	
})(jQuery);
(function($) {
elFinder.prototype.view = function(fm, el) {
	var self = this;
	this.fm = fm;
	/**
	 * Object. Mimetypes to kinds mapping
	 **/
	this.kinds = {
		'unknown'                       : 'Unknown',
		'directory'                     : 'Folder',
		'file'                          : 'File',
		'link'                          : 'Link',
		'symlink'                       : 'Alias',
		'symlink-broken'                : 'Broken alias',
		'application/x-empty'           : 'Plain text',
		'application/postscript'        : 'Postscript document',
		'application/octet-stream'      : 'Application',
		'application/vnd.ms-office'     : 'Microsoft Office document',
		'application/vnd.ms-word'       : 'Microsoft Word document',  
	    'application/vnd.ms-excel'      : 'Microsoft Excel document',
		'application/vnd.ms-powerpoint' : 'Microsoft Powerpoint presentation',
		'application/pdf'               : 'Portable Document Format (PDF)',
		'application/vnd.oasis.opendocument.text' : 'Open Office document',
		'application/x-shockwave-flash' : 'Flash application',
		'application/xml'               : 'XML document', 
		'application/x-bittorrent'      : 'Bittorrent file',
		'application/x-7z-compressed'   : '7z archive',
		'application/x-tar'             : 'TAR archive', 
	    'application/x-gzip'            : 'GZIP archive', 
	    'application/x-bzip2'           : 'BZIP archive', 
	    'application/zip'               : 'ZIP archive',  
	    'application/x-rar'             : 'RAR archive',
		'application/javascript'        : 'Javascript application',
		'text/plain'                    : 'Plain text',
	    'text/x-php'                    : 'PHP source',
		'text/html'                     : 'HTML document', 
		'text/javascript'               : 'Javascript source',
		'text/css'                      : 'CSS style sheet',  
	    'text/rtf'                      : 'Rich Text Format (RTF)',
		'text/rtfd'                     : 'RTF with attachments (RTFD)',
		'text/x-c'                      : 'C source', 
		'text/x-c++'                    : 'C++ source', 
		'text/x-shellscript'            : 'Unix shell script',
	    'text/x-python'                 : 'Python source',
		'text/x-java'                   : 'Java source',
		'text/x-ruby'                   : 'Ruby source',
		'text/x-perl'                   : 'Perl script',
	    'text/xml'                      : 'XML document', 
		'image/x-ms-bmp'                : 'BMP image',
	    'image/jpeg'                    : 'JPEG image',   
	    'image/gif'                     : 'GIF Image',    
	    'image/png'                     : 'PNG image',
		'image/x-targa'                 : 'TGA image',
	    'image/tiff'                    : 'TIFF image',   
	    'image/vnd.adobe.photoshop'     : 'Adobe Photoshop image',
		'audio/mpeg'                    : 'MPEG audio',  
		'audio/midi'                    : 'MIDI audio',
		'audio/ogg'                     : 'Ogg Vorbis audio',
		'audio/mp4'                     : 'MP4 audio',
		'audio/wav'                     : 'WAV audio',
		'video/x-dv'                    : 'DV video',
		'video/mp4'                     : 'MP4 video',
		'video/mpeg'                    : 'MPEG video',  
		'video/x-msvideo'               : 'AVI video',
		'video/quicktime'               : 'Quicktime video',
		'video/x-ms-wmv'                : 'WM video',   
		'video/x-flv'                   : 'Flash video',
		'video/x-matroska'              : 'Matroska video'
	}
	
	this.tlb = $('<ul />');
	this.browse = $('<div class="page-header-text">web Client</div>');
	this.ldwsnav = $('<div class="g-hd-ldws"/>').resizable({handles : 'e', autoHide : true, minWidth : 200, maxWidth: 500});
	this.nav = $('<div class="el-finder-nav"/>').resizable({handles : 'e', autoHide : true, minWidth : 200, maxWidth: 500});
	this.cwd = $('<div class="el-finder-cwd"/>').attr('unselectable', 'on');
	this.spn = $('<div class="el-finder-spinner"/>');
	this.err = $('<p class="el-finder-err"><strong/></p>').click(function() { $(this).hide(); });
	this.nfo = $('<div class="el-finder-stat"/>');
	this.pth = $('<div class="el-finder-path"/>');
	this.sel = $('<div class="el-finder-sel"/>');
	this.stb = $('<div class="el-finder-statusbar"/>')
		.append(this.pth)
		.append(this.nfo)
		.append(this.sel);
	this.wrz = $('<div class="el-finder-workzone" />')
		.append(this.nav)
		.append(this.browse)
		.append($('<div class="el-finder-toolbar" />').append(this.tlb))
		.append(this.cwd)
		.append(this.spn)
		.append(this.err)
		.append('<div style="clear:both" />');
	this.win = $(el).empty().attr('id', this.fm.id).addClass('el-finder '+(fm.options.cssClass||''))		
		.append(this.wrz);

//	this.tree = $('<ul class="el-finder-tree"></ul>').appendTo(this.nav);
	this.tree = $('<ul class="el-finder-tree"></ul>');
	this.ldwstree = $('<div class="g-mn"></div>').appendTo(this.nav);
//	this.plc  = $('<ul class="el-finder-places"><li><a href="#" class="el-finder-places-root"><div/>'+this.fm.i18n(this.fm.options.places)+'</a><ul/></li></ul>').hide();
	this.plc  = $('<a id="home-icon" href="/" style="display:block"><img src="../images/icons/logo.png"></a>').hide();

//	this.nav[this.fm.options.placesFirst ? 'prepend' : 'append'](this.plc);
	this.userinfo = $('#inner-page-header #account-header .top-level-nav-item');
	/*
	 * Render ajax spinner
	*/
	this.spinner = function(show) {
		this.win.toggleClass('el-finder-disabled', show);
		this.spn.toggle(show);
	}
	
	/*
	 * Display ajax error
	*/
	this.fatal = function(t) {
		self.error(t.status != '404' ? 'Invalid backend configuration' : 'Unable to connect to backend')
	}
	/*
	 * Render error
	*/
	this.error = function(err, data) {
		this.fm.lock();
		this.err.show().children('strong').html(this.fm.i18n(err)+'!'+this.formatErrorData(data));
		setTimeout(function() { 
			self.err.fadeOut('slow');			
			if(err=="SignatureMismatch"||err=="userSessionNotFinded"||err=="Invalid backend configuration"){
				window.location.replace("../login");
			} 
			if(err=="NotExist"){				
				self.fm.cookie(self.fm.lCookie, '/VsWRZpM+PU=');
				window.location.replace("../login");
			} 
		}, 4000);
	}
	
	if($.browser.msie){
		if($.browser.version==6.0||$.browser.version==7.0||$.browser.version==8.0||$.browser.version==9.0){
			this.activex = $('<OBJECT ID="ldupload" CLASSID="CLSID:2BF75C11-3286-4266-AA41-7CD1891B96F6" CODEBASE="../res/ldupload.CAB#version=3,0,0,2" WIDTH="100%" height="20%"></OBJECT>');			
		}
	}
	/*
	 * Render current Browse
	*/
	this.renderBrowse = function(){
		this.browse.empty();
        html = '';
		html='<a id="breadcrumbs-box" class="">'+'<img class="sprite sprite_web s_web_folder_arrow_32 " src="../images/icons/icon_spacer.gif">'+'</a>';
		html+='<img src="../images/icons/icon_spacer.gif" class="sprite sprite_web s_web_chevron breadcrumb_spacer">';
		for (var inner in this.fm.cwd.path) {	
			if(this.fm.cwd.path[inner].path==this.fm.cwd.hash){
				html+=this.fm.cwd.path[inner].name;
			}else if(typeof(this.fm.cwd.path[inner].name)!="undefined"){
				html+='<a key="'+this.fm.cwd.path[inner].path+'" class="ui-droppable" href="#">'+ this.fm.cwd.path[inner].name +'</a>';
				html+='<img src="../images/icons/icon_spacer.gif" class="sprite sprite_web s_web_chevron breadcrumb_spacer">';
			}
		}		
		this.browse.append(html);
	}
	/*
	 * Render ldwsnav
	*/
	this.renderLdwsnav = function(tree) {		
//		var a = $('<a id="home-icon" href="/"><img src="../images/icons/logo.png"></a>');
		this.ldwstree.empty();
		var ul=$('<ul id="main-nav"></ul>');
		for(var key in tree.dirs){
			if(typeof(tree.dirs[key].target)!="undefined"){
				var li = $('<li></li>');
				var a = $('<a key="'+tree.dirs[key].target+'" nav="'+tree.dirs[key].nav+'" href="#"></a>');
				if(tree.dirs[key].selected==true){
					li.addClass("selected");
				}
//				var span = $('<span class="nav-icon"></span>');
//				var html = '<i class="icon icon-file"></i>'
//				          +       '<span class="sprite-frame small icon-left">'
//				          +          '<img class=" sprite sprite_web '+tree.dirs[key].classname+'" src="../images/icons/icon_spacer-vflN3BYt2.gif">'
//				          +       '</span>'
//				          +       '<span class="sprite-text">'
//				          +          '<span class="sprite-text-inner">'+tree.dirs[key].text+'</span>'
//				          +       '</span>'
//				          +'</span>';
				var html = '<i class="icon '+tree.dirs[key].classname+'"></i>'
			          +    '<span class="text">'+tree.dirs[key].text+'</span>'
			          +    '<span class="bg"></span>';
				a.html(html);			
//				a.append(span);
				ul.append(li.append(a));
			}
		}
		
		var clientsdownhtml ='<div class="clients-download" style="display: block;">'
						    +'<ul class="clients-down">'
						        +'<li class="clients-down-pc">'
						            +'<a target="_blank" href="/loongdiskweb/app/Windows" title="PC客户端下载"> <i title="PC客户端下载" class="icon icon-windows"></i><span class="txt">PC</span>'
						            +'</a>'
						        +'</li>'
						        +'<li>'
						            +'<a target="_blank" href="/loongdiskweb/app/Mac" title="iPhone苹果版下载"> <i title="iPhone苹果版下载" class="icon icon-iphone"></i><span class="txt">iPhone</span>'
						            +'</a>'
						        +'</li>'
						        +'<li>'
						            +'<a target="_blank" href="/loongdiskweb/app/Android" title="Android安卓版下载"> <i title="Android安卓版下载" class="icon icon-android"></i><span class="txt">Android</span>'
						            +'</a>'
						        +'</li>'
						    +'</ul>'    
						+'</div>';
		var clientsdown = $(clientsdownhtml);
//		ul.append(clientsdown);
		this.ldwstree.append(ul);
		this.ldwstree.append(clientsdown);
		var webversion = '<div class="web-version">' 
						  	+'<a href="javascript:;">程序版本：'+this.fm.options.version+'</a>'
						 +'</div>';
		this.ldwstree.append($(webversion));
		
		if(typeof(this.activex)!=="undefined"){			
			this.ldwstree.append(this.activex);			
		}
		this.fm.options.places && this.renderPlaces();
	}
	/*
	 * Render navigation panel with dirs tree
	*/
	this.renderNav = function(tree) {
		var d = tree.dirs.length ? traverse(tree.dirs) : '',
			li = '<li><a href="#" class="el-finder-tree-root" key="'+tree.hash+'"><div'+(d ? ' class="collapsed expanded"' : '')+'/>'+tree.name+'</a>'+d+'</li>';
		this.tree.html(li);
		
		this.fm.options.places && this.renderPlaces();
		
		function traverse(tree) {
			var i, hash, c, html = '<ul style="display:none">';
			for (i=0; i < tree.length; i++) {
				if (!tree[i].name || !tree[i].hash) {
					continue;
				}
				c = '';
				if (!tree[i].read && !tree[i].write) {
					c = 'noaccess';
				} else if (!tree[i].read) {
					c = 'dropbox';
				} else if (!tree[i].write) {
					c = 'readonly';
				} 
				
				html += '<li><a href="#" class="'+c+'" key="'+tree[i].hash+'"><div'+(tree[i].dirs.length ? ' class="collapsed"' : '')+'/>'+tree[i].name+'</a>';

				if (tree[i].dirs.length) {
					html += traverse(tree[i].dirs);
				}
				html += '</li>';
			}
			return html +'</ul>';
		}
	}
	
	/*
	 * Render places
	*/
	this.renderPlaces = function() {
		var i, c, 
			pl = this.fm.getPlaces(),	
			ul = this.plc.show().find('ul').empty().hide();
		$('div:first', this.plc).removeClass('collapsed expanded');

		if (pl.length) {
			pl.sort(function(a, b) {
				var _a = self.tree.find('a[key="'+a+'"]').text()||'',
					_b = self.tree.find('a[key="'+b+'"]').text()||'';
				return _a.localeCompare(_b);
			});
			
			for (i=0; i < pl.length; i++) {
				if ((c = this.tree.find('a[key="'+pl[i]+'"]:not(.dropbox)').parent()) && c.length) {
					ul.append(c.clone().children('ul').remove().end().find('div').removeClass('collapsed expanded').end());
				} else {
					this.fm.removePlace(pl[i]);
				}
			};
			ul.children().length && $('div:first', this.plc).addClass('collapsed');
		}
	}
	
	/*
	 * Render current directory
	*/
	this.renderCwd = function() {
		this.cwd.empty();
		
		var num  = 0, size = 0, html = '';
		for (var hash in this.fm.cdc) {
			num++;
			size += this.fm.cdc[hash].size;
			html += this.fm.options.view == 'icons'
				? this.renderIcon(this.fm.cdc[hash])
				: this.renderRow(this.fm.cdc[hash], num%2);
		}
		if (this.fm.options.view == 'icons') {
			this.cwd.append(html);
		} else {
			this.cwd.append('<table><tr><th colspan="2">'+this.fm.i18n('Name')+'</th><th>'+this.fm.i18n('Modified')+'</th><th>'+this.fm.i18n('Size')+'</th><th colspan="2">'+this.fm.i18n('Kind')+'</th></tr>'+html+'</table>');
		}
		
		this.pth.text(fm.cwd.rel);
		this.nfo.text(fm.i18n('items')+': '+num+', '+this.formatSize(size));
		this.sel.empty();
	}

	/*
	 * Render one file as icon
	*/
	this.renderIcon = function(f) {
		var str = '<p'+(f.tmb ? ' style="'+"background:url('"+f.tmb+"') 0 0 no-repeat"+'"' : '')+'/><label>'+this.formatName(f.name)+'</label>';
		if (f.link || f.mime == 'symlink-broken') {
			str += '<em/>';
		}
		if (!f.read && !f.write) {
			str += '<em class="noaccess"/>';
		} else if (f.read && !f.write) {
			str += '<em class="readonly"/>';
		} else if (!f.read && f.write) {
			str += '<em class="'+(f.mime == 'directory' ? 'dropbox' :'noread')+'" />';
		}
		return '<div class="'+this.mime2class(f.mime)+'" key="'+f.hash+'">'+str+'</div>';
	}

	/*
	 * Render one file as table row
	*/
	this.renderRow = function(f, odd) {
		var str = f.link || f.mime =='symlink-broken' ? '<em/>' : '';
		if (!f.read && !f.write) {
			str += '<em class="noaccess"/>';
		} else if (f.read && !f.write) {
			str += '<em class="readonly"/>';
		} else if (!f.read && f.write) {
			str += '<em class="'+(f.mime == 'directory' ? 'dropbox' :'noread')+'" />';
		}
		return '<tr key="'+f.hash+'" class="'+self.mime2class(f.mime)+(odd ? ' el-finder-row-odd' : '')+'">'
			+'<td class="icon"><p>'+str+'</p></td>'
			+'<td style="width:45%">'+f.name+'</td>'
			+'<td style="width:25%">'+self.formatDate(f.date)+'</td>'
			+'<td style="width:10%">'+self.formatSize(f.size)+'</td>'
			+'<td>'+self.mime2kind(f.link ? 'symlink' : f.mime)+'</td>'
			+'<td>'+(self.mime2class(f.mime)!="directory"?'<a style="margin-right: 0px;" class="shmodel-file" href="#" title="生成外链"><img class="sprite sprite_web s_web_s_link " src="../images/icons/icon_spacer.gif"></a>':"")+'</td></tr>';
	}

	/*
	 * Re-render file (after editing)
	*/
	this.updateFile = function(f) {
		var e = this.cwd.find('[key="'+f.hash+'"]');
		e.replaceWith(e[0].nodeName == 'DIV' ? this.renderIcon(f) : this.renderRow(f));
	}

	/*
	 * Update info about selected files
	*/
	this.selectedInfo = function() {
		var i, s = 0, sel;
		
		if (self.fm.selected.length) {
			sel = this.fm.getSelected();
			for (i=0; i<sel.length; i++) {
				s += sel[i].size;
			}
		}
		this.sel.text(i>0 ? this.fm.i18n('selected items')+': '+sel.length+', '+this.formatSize(s) : '');
	}

	/*
	 * Return wraped file name if needed
	*/
	this.formatName = function(n) {
		var w = self.fm.options.wrap;
		if (w>0) {
			if (n.length > w*2) {
				return n.substr(0, w)+"&shy;"+n.substr(w, w-5)+"&hellip;"+n.substr(n.length-3);
			} else if (n.length > w) {
				return n.substr(0, w)+"&shy;"+n.substr(w);
			}
		}
		return n;
	}

	/*
	 * Return error message
	*/
	this.formatErrorData = function(data) {
		var i, err = ''
		if (typeof(data) == 'object') {
			err = '<br />';
			for (i in data) {
				err += i+' '+self.fm.i18n(data[i])+'<br />';
			}
		}
		return err;
	}

	/*
	 * Convert mimetype into css class
	*/
	this.mime2class = function(mime) {
		return mime.replace('/' , ' ').replace(/\./g, '-');
	}

	/*
	 * Return localized date
	*/
	this.formatDate = function(d) {
		//return d.replace(/([a-z]+)\s/i, function(a1, a2) { return self.fm.i18n(a2)+' '; });
		return d==""?d:new Date(d).Format("yyyy-MM-dd hh:mm:ss");
	}

	/*
	 * Return formated file size
	*/
	this.formatSize = function(s) {
		var n = 1, u = 'bytes';
		if (s > 1073741824) {
			n = 1073741824;
			u = 'GB';
		} else if (s > 1048576) {
            n = 1048576;
            u = 'MB';
        } else if (s > 1024) {
            n = 1024;
            u = 'KB';
        }
        return Math.round(s/n)+' '+u;
	}

	/*
	 * Return localized string with file permissions
	*/
	this.formatPermissions = function(r, w, rm) {
		var p = [];
		r  && p.push(self.fm.i18n('read'));
		w  && p.push(self.fm.i18n('write'));
		rm && p.push(self.fm.i18n('remove'));
		return p.join('/');
	}
	
	/*
	 * Return kind of file
	*/
	this.mime2kind = function(mime) {
		return this.fm.i18n(this.kinds[mime]||'unknown');
	}
	
}

})(jQuery);
/**
 * @class elFinder user Interface. 
 * @author dio dio@std42.ru
 **/
(function($) {
elFinder.prototype.ui = function(fm) {
	
	var self        = this;
	this.fm         = fm;
	this.cmd        = {};
	this.buttons    = {};
	this.menu       = $('<div class="el-finder-contextmenu" />').appendTo(document.body).hide();
	this.dockButton = $('<div class="el-finder-dock-button" title="'+self.fm.i18n('Dock/undock filemanager window')+'" />');
	/**
	 * Create and return dialog.
	 *
	 * @param  String|DOMElement  dialog content
	 * @param  Object             dialog options
	 * @return jQuery
	 */
	this.dialog = function(content, options) {
		return $('<div/>').append(content).dialog(options);
	};
	this.notify = this.dialog('', {
		cssClass  : 'elfinder-dialog-notify',
		position  : {top : '12px', right : '12px'},
		resizable : false,
		autoOpen  : false,
		title     : '&nbsp;',
		width     : 280
	});
	this.exec = function(cmd, arg) {
		if (this.cmd[cmd]) {
			if (cmd != 'open' && !this.cmd[cmd].isAllowed()) {
				return this.fm.view.error('Command not allowed');
			}
			if (!this.fm.locked) {
				this.fm.quickLook.hide();
				$('.el-finder-info').remove();
				this.cmd[cmd].exec(arg);
				this.update();
			}
		}
	}
	
	this.cmdName = function(cmd) {
		if (this.cmd[cmd] && this.cmd[cmd].name) {
			return cmd == 'archive' && this.fm.params.archives.length == 1
				? this.fm.i18n('Create')+' '+this.fm.view.mime2kind(this.fm.params.archives[0]).toLowerCase()
				: this.fm.i18n(this.cmd[cmd].name);
		}
		return cmd;
	}
	
	this.isCmdAllowed = function(cmd) {
		return self.cmd[cmd] && self.cmd[cmd].isAllowed();
	}
	
	this.execIfAllowed = function(cmd) {
		this.isCmdAllowed(cmd) && this.exec(cmd);
	}
	
	this.includeInCm = function(cmd, t) {
		return this.isCmdAllowed(cmd) && this.cmd[cmd].cm(t);
	}
	
	this.showMenu = function(e) {
		var t, win, size, id = '';
		this.hideMenu();
		
		if (!self.fm.selected.length) {
			t = 'cwd';
		} else if (self.fm.selected.length == 1) {
			t = 'file';
		} else {
			t = 'group';
		}
		
		menu(t);
		
		win = $(window);
		size = {
	    	height : win.height(),
      		width  : win.width(),
      		sT     : win.scrollTop(),
      		cW     : this.menu.width(),
      		cH     : this.menu.height()
	    };
		this.menu.css({
				left : ((e.clientX + size.cW) > size.width ? ( e.clientX - size.cW) : e.clientX),
				top  : ((e.clientY + size.cH) > size.height && e.clientY > size.cH ? (e.clientY + size.sT - size.cH) : e.clientY + size.sT)
			})
			.show()
			.find('div[name]')
			.hover(
				function() { 
					var t = $(this), s = t.children('div'), w;
					t.addClass('hover');
					if (s.length) {
						if (!s.attr('pos')) {
							w  = t.outerWidth();
							s.css($(window).width() - w - t.offset().left > s.width() ? 'left' : 'right', w-5).attr('pos', true);
						}
						s.show();
					}
				}, 
				function() { $(this).removeClass('hover').children('div').hide(); }
			).click(function(e) {
				e.stopPropagation();
				var t = $(this);
				if (!t.children('div').length) {
					self.hideMenu();
					self.exec(t.attr('name'), t.attr('argc'));
				}
			});
		function menu(t) {
			var i, j, a, html, l, src = self.fm.options.contextmenu[t]||[];
			for (i=0; i < src.length; i++) {
				if (src[i] == 'delim') {
					self.menu.children().length && !self.menu.children(':last').hasClass('delim') && self.menu.append('<div class="delim" />');
				} else if (self.fm.ui.includeInCm(src[i], t)) {
					a = self.cmd[src[i]].argc();
					html = '';

					if (a.length) {
						html = '<span/><div class="el-finder-contextmenu-sub" style="z-index:'+(parseInt(self.menu.css('z-index'))+1)+'">';
						for (var j=0; j < a.length; j++) {
							html += '<div name="'+src[i]+'" argc="'+a[j].argc+'" class="'+a[j]['class']+'">'+a[j].text+'</div>';
						};
						html += '</div>';
					}
					self.menu.append('<div class="'+src[i]+'" name="'+src[i]+'">'+html+self.cmdName(src[i])+'</div>');
				}
			};
		}
	
	}
	
	this.hideMenu = function() {
		this.menu.hide().empty();
	}
	
	this.update = function() {
		for (var i in this.buttons) {
			this.buttons[i].toggleClass('disabled', !this.cmd[i].isAllowed());
		}
	}
	
	this.init = function(disabled) {
		var i, j, n, c=false, zindex = 2, z, t = this.fm.options.toolbar;
		/* disable select command if there is no callback for it */
		if (!this.fm.options.editorCallback) {
			disabled.push('select');
		}
		/* disable archive command if no archivers enabled  */
		if (!this.fm.params.archives.length && $.inArray('archive', disabled) == -1) {
			disabled.push('archive');
		}
		for (i in this.commands) {
			if ($.inArray(i, disabled) == -1) {
				this.commands[i].prototype = this.command.prototype;
				this.cmd[i] = new this.commands[i](this.fm);
			}
		}

		for (i=0; i<t.length; i++) {
			if (c) {
				this.fm.view.tlb.append('<li class="delim" />');
			}
			c = false;
			for (j=0; j<t[i].length; j++) {
				n = t[i][j];
				if (this.cmd[n]) {
					c = true;
					this.buttons[n] = $('<li class="'+n+'" title="'+this.cmdName(n)+'" name="'+n+'" />')
						.appendTo(this.fm.view.tlb)
						.click(function(e) { e.stopPropagation(); })
						.bind('click', (function(ui){ return function() {  
								!$(this).hasClass('disabled') && ui.exec($(this).attr('name'));
							} })(this)
						).hover(
							function() { !$(this).hasClass('disabled') && $(this).addClass('el-finder-tb-hover')},
							function() { $(this).removeClass('el-finder-tb-hover')}
						);
				}
			}
		}
		this.update();
		/* set z-index for context menu */
		this.menu.css('z-index', this.fm.zIndex);
		/*
		if (this.fm.options.docked) {
			this.dockButton.hover(
				function() { $(this).addClass('el-finder-dock-button-hover')},
				function() { $(this).removeClass('el-finder-dock-button-hover')}
			).click(function() { 
				self.fm.view.win.attr('undocked') ? self.fm.dock() : self.fm.undock();
				$(this).trigger('mouseout');
			}).prependTo(this.fm.view.tlb);
		}*/
		
	}

}
/**
 * @class elFinder user Interface Command. 
 * @author dio dio@std42.ru
 **/
elFinder.prototype.ui.prototype.command = function(fm) {  }

/**
 * Return true if command can be applied now 
 * @return Boolean
 **/
elFinder.prototype.ui.prototype.command.prototype.isAllowed = function() {
	return true;
}

/**
 * Return true if command can be included in contextmenu of required type
 * @param  String  contextmenu type (cwd|group|file)
 * @return Boolean
 **/
elFinder.prototype.ui.prototype.command.prototype.cm = function(t) {
	return false;
}

/**
 * Return not empty array if command required submenu in contextmenu
 * @return Array
 **/
elFinder.prototype.ui.prototype.command.prototype.argc = function(t) {
	return [];
}


elFinder.prototype.ui.prototype.commands = {
	
	/**
	 * @class Go into previous folder
	 * @param Object  elFinder
	 **/
	back : function(fm) {
		var self = this;
		this.name = 'Back';
		this.fm = fm;
		
		this.exec = function() {
			if (this.fm.history.length) {
				var backitem = this.fm.history.pop();
				this.fm.ajax({ cmd : 'open', target : backitem.hash,masterdc : backitem.masterdc, ownerid : backitem.ownerid	}, function(data) {
					self.fm.reload(data);
				});
			}
		}
		
		this.isAllowed = function() {
			return this.fm.history.length
		}
		
	},
	
	/**
	 * @class Reload current directory and navigation panel
	 * @param Object  elFinder
	 **/
	reload : function(fm) {
		var self  = this;
		this.name = 'Reload';
		this.fm   = fm;
		
		this.exec = function() {
			this.fm.ajax({ cmd : 'open', target : this.fm.cwd.hash,nav:this.fm.cwd.nav, tree : true,masterdc : self.fm.cwd.masterdc, ownerid : self.fm.cwd.ownerid }, function(data) {
				self.fm.reload(data);
			});
		}
		
		this.cm = function(t) {
			return t == 'cwd';
		}
	},
	
	/**
	 * @class Open file/folder
	 * @param Object  elFinder
	 **/
	open : function(fm) {
		var self  = this;
		this.name = 'Open';
		this.fm   = fm;
		
		/**
		 * Open file/folder
		 * @param String  file/folder id (only from click on nav tree)
		 **/
		this.exec = function(dir) {
			var t = null;
			if (dir) {
				t = {
					hash : $(dir).attr('key'),
					mime : 'directory',
					nav : $(dir).attr('nav'),
					masterdc : $(dir).attr('masterdc'),
					ownerid : $(dir).attr('ownerid'),
					read : !$(dir).hasClass('noaccess') && !$(dir).hasClass('dropbox')
				}
			} else {
				t = this.fm.getSelected(0);
			}
			if (!t.hash) {
				return; 
			}
			if (!t.read) {
				return this.fm.view.error('Access denied');
			}
			if (t.type == 'link' && !t.link) {
				return this.fm.view.error('Unable to open broken link');
			}
			if (t.mime == 'directory') {
				openDir(t.link||t.hash);
			} else {
				openFile(t);
			}
			
			function openDir(id) {
				self.fm.history.push(self.fm.cwd);
				self.fm.ajax({ cmd : 'open', target : id,nav:t.nav!=null?t.nav:self.fm.cwd.nav,masterdc:t.masterdc,ownerid:t.ownerid }, function(data) {
					if(typeof(data.error)!= "undefined" ){
						return;
					}
					self.fm.reload(data);
				});
			}
			
			function openFile(f) {
				var s, ws = '';
				if (f.dim) {
					s  = f.dim.split('x');
					ws = 'width='+(parseInt(s[0])+20)+',height='+(parseInt(s[1])+20)+',';
				}
				window.open(f.url||self.fm.options.url+'?cmd=open&masterdc='+f.masterdc+'&ownerid='+f.ownerid+'&current='+(f.parent||self.fm.cwd.hash)+'&target='+(f.link||f.hash), false, 'top=50,left=50,'+ws+'scrollbars=yes,resizable=yes');
			}
		}
	
		this.isAllowed = function() {
			return this.fm.selected.length == 1 && this.fm.getSelected(0).read;
		}
		
		this.cm = function(t) {
			return t == 'file';
		}
		
	},
	
	/**
	 * @class. Return file url
	 * @param Object  elFinder
	 **/
	select : function(fm) {
		this.name = 'Select file';
		this.fm   = fm;
				
		if (fm.options.selectMultiple) {
            this.exec = function() {

                var selected = $(fm.getSelected()).map(function() {
                    return fm.options.cutURL == 'root' ? this.url.substr(fm.params.url.length) : this.url.replace(new RegExp('^('+fm.options.cutURL+')'), ''); 
                });  

                fm.options.editorCallback(selected);

                if (fm.options.closeOnEditorCallback) {
                    fm.dock();
                    fm.close();
                }
            }
        } else {
            this.exec = function() { 
                var f = this.fm.getSelected(0);

                if (!f.url) {
                    return this.fm.view.error('File URL disabled by connector config');
                }
                this.fm.options.editorCallback(this.fm.options.cutURL == 'root' ? f.url.substr(this.fm.params.url.length) : f.url.replace(new RegExp('^('+this.fm.options.cutURL+')'), ''));

                if (this.fm.options.closeOnEditorCallback) {
                    this.fm.dock();
                    this.fm.close();
                    this.fm.destroy();
                }
				
            }
        }

        this.isAllowed = function() {
            return ((this.fm.options.selectMultiple && this.fm.selected.length >= 1) || this.fm.selected.length == 1) && !/(symlink\-broken|directory)/.test(this.fm.getSelected(0).mime);
        }
		
		this.cm = function(t) {
			return t != 'cwd';
			// return t == 'file';
		}
	},
	
	/**
	 * @class. Open/close quickLook window
	 * @param Object  elFinder
	 **/
	quicklook : function(fm) {
		var self  = this;
		this.name = 'Preview with Quick Look';
		this.fm   = fm;
		
		this.exec = function() {
			self.fm.quickLook.toggle();
		}
		
		this.isAllowed = function() {
			return this.fm.selected.length == 1;
		}
		
		this.cm = function() {
			return true;
		}
	},
	
	/**
	 * @class Display files/folders info in dialog window
	 * @param Object  elFinder
	 **/
	info : function(fm) {
		var self  = this;
		this.name = 'Get info';
		this.fm   = fm;
		
		/**
		 * Open dialog windows for each selected file/folder or for current folder
		 **/
		this.exec = function() {
			var f, s, cnt = this.fm.selected.length, w = $(window).width(), h = $(window).height();
			this.fm.lockShortcuts(true);
			if (!cnt) {
				/** nothing selected - show cwd info **/
				info(self.fm.cwd);
			} else {
				/** show info for each selected obj **/
				$.each(this.fm.getSelected(), function() {
					info(this);
				});
			}
			
			function info(f) {
				var p = ['50%', '50%'], x, y, d, 
					tb = '<table cellspacing="0"><tr><td>'+self.fm.i18n('Name')+'</td><td>'+f.name+'</td></tr><tr><td>'+self.fm.i18n('Kind')+'</td><td>'+self.fm.view.mime2kind(f.link ? 'symlink' : f.mime)+'</td></tr><tr><td>'+self.fm.i18n('Size')+'</td><td>'+self.fm.view.formatSize(f.size)+'</td></tr><tr><td>'+self.fm.i18n('Modified')+'</td><td>'+self.fm.view.formatDate(f.date)+'</td></tr><tr><td>'+self.fm.i18n('Permissions')+'</td><td>'+self.fm.view.formatPermissions(f.read, f.write, f.rm)+'</td></tr>';
				
				if (f.link) {
					tb += '<tr><td>'+self.fm.i18n('Link to')+'</td><td>'+f.linkTo+'</td></tr>';
				}
				if (f.dim) {
					tb += '<tr><td>'+self.fm.i18n('Dimensions')+'</td><td>'+f.dim+' px.</td></tr>';
				}
				if (f.url) {
					tb += '<tr><td>'+self.fm.i18n('URL')+'</td><td><a href="'+f.url+'" target="_blank">'+f.url+'</a></td></tr>';
				}

				if (cnt>1) {
					d = $('.el-finder-dialog-info:last');
					if (!d.length) {
						x = Math.round(((w-350)/2)-(cnt*10));
						y = Math.round(((h-300)/2)-(cnt*10));
						p = [x>20?x:20, y>20?y:20];
					} else {
						x = d.offset().left+10;
						y = d.offset().top+10;
						p = [x<w-350 ? x : 20, y<h-300 ? y : 20];
					}
				}

				$('<div />').append(tb+'</table>').dialog({
					dialogClass : 'el-finder-dialog el-finder-dialog-info',
					width       : 390,
					position    : p,
					title       : self.fm.i18n(f.mime == 'directory' ? 'Folder info' : 'File info'),
					close       : function() { if (--cnt <= 0) { self.fm.lockShortcuts(); } $(this).dialog('destroy'); },
					buttons     : { Ok : function() { $(this).dialog('close'); }}
				});
			}
		}
	
		this.cm = function(t) {
			return true;
		}
	},
	
	/**
	 * @class Rename file/folder
	 * @param Object elFinder
	 **/
	rename : function(fm) {
		var self  = this;
		this.name = 'Rename';
		this.fm   = fm;
		
		this.exec = function() {
			var s = this.fm.getSelected(), el, c, input, f, n;
			
			if (s.length == 1) {
				f  = s[0];
				el = this.fm.view.cwd.find('[key="'+f.hash+'"]');
				c  = this.fm.options.view == 'icons' ? el.children('label') : el.find('td').eq(1);
				n  = c.html();
				input = $('<input type="text" />').val(f.name).appendTo(c.empty())
					.bind('change blur', rename)
					.keydown(function(e) {
						e.stopPropagation();
						if (e.keyCode == 27) {
							restore();
						} else if (e.keyCode == 13) {
							if (f.name == input.val()) {
								restore();
							} else {
								$(this).trigger('change');
							}
						}
					})
					.click(function(e) { e.stopPropagation(); })
					.select()
					.focus();
				this.fm.lockShortcuts(true);
			}

			function restore() {
				c.html(n);
				self.fm.lockShortcuts();
			}
			
			function rename() {
				if (!self.fm.locked) {
					var err, name = input.val();
					if (f.name == input.val()) {
						return restore();
					}

					if (!self.fm.isValidName(name)) {
						err = 'Invalid name';
					} else if (self.fm.fileExists(name)) {
						err = 'File or folder with the same name already exists';
					}
					
					if (err) {
						self.fm.view.error(err);
						el.addClass('ui-selected');
						self.fm.lockShortcuts(true);
						return input.select().focus();
					}
					
					self.fm.ajax({cmd : 'rename', current : self.fm.cwd.hash, target : f.hash, name : name,mime:f.mime,masterdc : self.fm.cwd.masterdc, ownerid : self.fm.cwd.ownerid}, function(data) {
						if (data.error) {
							restore();
						} else {
							f.mime == 'directory' && self.fm.removePlace(f.hash) && self.fm.addPlace(data.target);
							self.fm.reload(data); 
						}
					}, { force : true });
				}
			}
		}
		
		/**
		 * Return true if only one file selected and has write perms and current dir has write perms
		 * @return Boolean
		 */
		this.isAllowed = function() {
			return this.fm.cwd.write && this.fm.getSelected(0).write;
		}

		this.cm = function(t) {
			return t == 'file';
		}
	},
	
	/**
	 * @class Copy file/folder to "clipboard"
	 * @param Object elFinder
	 **/
	copy : function(fm) {
		this.name = 'Copy';
		this.fm   = fm;
		
		this.exec = function() {
			this.fm.setBuffer(this.fm.selected);
		}
		
		this.isAllowed = function() {
			if (this.fm.selected.length) {
				var s = this.fm.getSelected(), l = s.length;
				while (l--) {
					if (s[l].read) {
						return true;
					}
				}
			}
			return false;
		}
		
		this.cm = function(t) {
			return t != 'cwd';
		}
	},
	download : function(fm) {
		this.name = 'Download';
		this.fm   = fm;
		
		this.exec = function() {
//			this.fm.setBuffer(this.fm.selected);
//			console.log('buffer===============',this.fm.buffer);
			var names=[];
			for (i=0; i<this.fm.selected.length; i++) {
				id = this.fm.selected[i]; 
				f = this.fm.cdc[id];
				if (f && f.read && f.type != 'link') {
					names.push(f.name);
				}
			}		
			var o = {
					cmd       : 'download',
					current   : this.fm.cwd.hash,
					names     : names,
					masterdc : this.fm.cwd.masterdc, 
					ownerid : this.fm.cwd.ownerid
				};
			this.fm.ajax(o, function(data) {
				window.location.href=data.url;
			}, {force : true});
		}
		
		this.isAllowed = function() {
			if (this.fm.selected.length) {
				var s = this.fm.getSelected(), l = s.length;
				while (l--) {
					if (s[l].read&&s[l].mime!="directory") {
						return true;
					}
				}
			}
			return false;
		}
		
		this.cm = function(t) {
			return t != 'cwd';
		}
	},
	
	/**
	 * @class Cut file/folder to "clipboard"
	 * @param Object elFinder
	 **/
	cut : function(fm) {
		this.name = 'Cut';
		this.fm   = fm;
		
		this.exec = function() {
			this.fm.setBuffer(this.fm.selected, 1);
		}
		
		this.isAllowed = function() {
			if (this.fm.selected.length) {
				var s = this.fm.getSelected(), l = s.length;
				while (l--) {
					if (s[l].read && s[l].rm) {
						return true;
					}
				}
			}
			return false;
		}
		
		this.cm = function(t) {
			return t != 'cwd';
		}
	},
	
	/**
	 * @class Paste file/folder from "clipboard"
	 * @param Object elFinder
	 **/
	paste : function(fm) {
		var self  = this;
		this.name = 'Paste';
		this.fm   = fm;
		
		this.exec = function() {
			var i, d, f, r, msg = '';
			if (!this.fm.buffer.dst) {
				this.fm.buffer.dst = this.fm.cwd.hash;
			}
			/**
			d = this.fm.view.tree.find('[key="'+this.fm.buffer.dst+'"]');
			if (!d.length || d.hasClass('noaccess') || d.hasClass('readonly')) {
				return this.fm.view.error('Access denied');
			}
			**/
			if (this.fm.buffer.src == this.fm.buffer.dst) {
				return this.fm.view.error('Unable to copy into itself');
			}
			var o = {
				cmd       : 'paste',
				current   : this.fm.cwd.hash,
				src       : this.fm.buffer.src,
				dst       : this.fm.buffer.dst,
				cut       : this.fm.buffer.cut,
				nav       : this.fm.cwd.nav,
				masterdc : self.fm.cwd.masterdc, 
				ownerid : self.fm.cwd.ownerid
			};
			if (this.fm.jquery>132) {
				o.targets = this.fm.buffer.files;
				o.names = this.fm.buffer.names;
				o.mimes = this.fm.buffer.mimes;
			} else {
				o['cwd'] = this.fm.cwd;
			}
			this.fm.ajax(o, function(data) {
				self.fm.clearBuffer();
				data.cdc && self.fm.reload(data);
			}, {force : true});
		}
		
		this.isAllowed = function() {
			return this.fm.buffer.files;
		}
		
		this.cm = function(t) {
			return t == 'cwd';
		}
	},
	
	/**
	 * @class Remove files/folders
	 * @param Object elFinder
	 **/
	rm : function(fm) {
		var self  = this;
		this.name = 'Remove';
		this.fm   = fm;
		
		this.exec = function () {
		    var i, ids = [],
		        s = this.fm.getSelected();
		    for (var i = 0; i < s.length; i++) {
		        if (!s[i].rm) {
		            return this.fm.view.error(s[i].name + ': ' + this.fm.i18n('Access denied'));
		        }
		        ids.push(s[i].hash + " " + s[i].mime);
		    };
		    if (ids.length) {
		        this.fm.lockShortcuts(true);
		        $('<div><div class="ui-state-error ui-corner-all"><span class="ui-icon ui-icon-alert"/><strong>' + this.fm.i18n('Are you sure you want to remove files?<br /> This cannot be undone!') + '</strong></div></div>')
		            .dialog({
		                title: self.fm.i18n('Confirmation required'),
		                dialogClass: 'el-finder-dialog',
		                width: 350,
		                close: function () {
		                    self.fm.lockShortcuts();
		                },
		                buttons: {
		                    '取消': function () {
		                        $(this).dialog('close');
		                    },
		                    '确定': function () {
		                        $(this).dialog('close');
		                        var o = {
		                            cmd: 'rm',
		                            current: self.fm.cwd.hash,
		                            nav: self.fm.cwd.nav,
		                            masterdc: self.fm.cwd.masterdc,
		                            ownerid: self.fm.cwd.ownerid
		                        };
		                        if (self.fm.jquery > 132) {
		                            o.targets = ids;
		                        } else {
		                            o['targets[]'] = ids;
		                        }
		                        self.fm.ajax(o, function (data) {
		                                data.tree && self.fm.reload(data);
		                                if (data.confirm) {
		                                    $('<div><div class="ui-state-error ui-corner-all"><span class="ui-icon ui-icon-alert"/><strong>' + self.fm.i18n('Directory not empty, Are you sure you want to remove this ?') + '</strong></div></div>')
		                                        .dialog({
		                                            title: self.fm.i18n('NotEmpty'),
		                                            dialogClass: 'el-finder-dialog',
		                                            width: 350,
		                                            close: function () {
		                                                self.fm.lockShortcuts();
		                                            },
		                                            buttons: {
		                                                '取消': function () {
		                                                    $(this).dialog('close');
		                                                },
		                                                '确定': function () {
		                                                    $(this).dialog('close');
		                                                    var o = {
		                                                        cmd: 'rm',
		                                                        current: self.fm.cwd.hash,
		                                                        nav: self.fm.cwd.nav,
		                                                        masterdc: self.fm.cwd.masterdc,
		                                                        ownerid: self.fm.cwd.ownerid,
		                                                        force: true
		                                                    };
		                                                    if (self.fm.jquery > 132) {
		                                                        o.targets = ids;
		                                                    } else {
		                                                        o['targets[]'] = ids;
		                                                    }
		                                                    self.fm.ajax(o, function (data) {
		                                                            data.tree && self.fm.reload(data);
		                                                        });
		                                                }
		                                            }
		                                        });
		                                }
		                            }, {force: true});
		                    }
		                }
		            });
		    }
		}
		
		this.isAllowed = function(f) {
			if (this.fm.selected.length) {
				var s = this.fm.getSelected(), l = s.length;
				while (l--) {
					if (s[l].rm) {
						return true;
					}
				}
			}
			return false;
		}
		
		this.cm = function(t) {
			return t != 'cwd';
		}
	},
	
	/**
	 * @class Create new folder
	 * @param Object  elFinder
	 **/
	mkdir : function(fm) {
		var self  = this;
		this.name = 'New folder';
		this.fm   = fm;
		
		this.exec = function() {
			self.fm.unselectAll();
			var n     = this.fm.uniqueName('untitled folder');
				input = $('<input type="text"/>').val(n);
				prev  = this.fm.view.cwd.find('.directory:last');
				f     = {name : n, hash : '', mime :'directory', read : true, write : true, date : '', size : 0},
				el    = this.fm.options.view == 'list' 
						? $(this.fm.view.renderRow(f)).children('td').eq(1).empty().append(input).end().end()
						: $(this.fm.view.renderIcon(f)).children('label').empty().append(input).end()
			el.addClass('directory ui-selected');
			if (prev.length) {
				el.insertAfter(prev);
			} else if (this.fm.options.view == 'list') {
				el.insertAfter(this.fm.view.cwd.find('tr').eq(0))
			} else {
				el.prependTo(this.fm.view.cwd)
			}
			self.fm.checkSelectedPos();
			input.select().focus()
				.click(function(e) { e.stopPropagation(); })
				.bind('change blur', mkdir)
				.keydown(function(e) {
					e.stopPropagation();
					if (e.keyCode == 27) {
						el.remove();
						self.fm.lockShortcuts();
					} else if (e.keyCode == 13) {
						mkdir();
					}
				});

			self.fm.lockShortcuts(true);

			function mkdir() {
				if (!self.fm.locked) {
					var err, name = input.val();
					if (!self.fm.isValidName(name)) {
						err = 'Invalid name';
					} else if (self.fm.fileExists(name)) {
						err = 'File or folder with the same name already exists';
					}
					if (err) {
						self.fm.view.error(err);
						self.fm.lockShortcuts(true);
						el.addClass('ui-selected');
						return input.select().focus();
					}
					
					self.fm.ajax({cmd : 'mkdir', current : self.fm.cwd.hash, name : name, masterdc : self.fm.cwd.masterdc, ownerid : self.fm.cwd.ownerid}, function(data) {						
						if (data.error) {				
							el.remove();
							return true;
						}
						self.fm.reload(data);
					}, {force : true});
				}
			}
		}
		
		this.isAllowed = function() {
			return this.fm.cwd.write;
		}
		
		this.cm = function(t) {
			return t == 'cwd';
		}
	},
	
	/**
	 * @class Create new text file
	 * @param Object  elFinder
	 **/
	mkfile : function(fm) {
		var self  = this;
		this.name = 'New text file';
		this.fm   = fm;
		
		this.exec = function() {
			self.fm.unselectAll();
			var n     = this.fm.uniqueName(this.fm.i18n('untitled file'), '.txt'),
				input = $('<input type="text"/>').val(n), 
				f     = {name : n, hash : '', mime :'text/plain', read : true, write : true, date : '', size : 0},
				el    = this.fm.options.view == 'list' 
					? $(this.fm.view.renderRow(f)).children('td').eq(1).empty().append(input).end().end()
					: $(this.fm.view.renderIcon(f)).children('label').empty().append(input).end();
					
			el.addClass('text ui-selected').appendTo(this.fm.options.view == 'list' ? self.fm.view.cwd.children('table') : self.fm.view.cwd);
			
			input.select().focus()
				.bind('change blur', mkfile)
				.click(function(e) { e.stopPropagation(); })
				.keydown(function(e) {
					e.stopPropagation();
					if (e.keyCode == 27) {
						el.remove();
						self.fm.lockShortcuts();
					} else if (e.keyCode == 13) {
						mkfile();
					}
				});
			self.fm.lockShortcuts(true);
			
			function mkfile() {
				if (!self.fm.locked) {
					var err, name = input.val();
					if (!self.fm.isValidName(name)) {
						err = 'Invalid name';
					} else if (self.fm.fileExists(name)) {
						err = 'File or folder with the same name already exists';
					}
					if (err) {
						self.fm.view.error(err);
						self.fm.lockShortcuts(true);
						el.addClass('ui-selected');
						return input.select().focus();
					}
					self.fm.ajax({cmd : 'mkfile', current : self.fm.cwd.hash, name : name,masterdc : self.fm.cwd.masterdc, ownerid : self.fm.cwd.ownerid}, function(data) {
						if (data.error) {
							el.remove();
//							el.addClass('ui-selected');
//							return input.select().focus();
							return true;
						}
						self.fm.reload(data);
					}, {force : true });
				}
			}
			
		}
		
		this.isAllowed = function(f) {
			return this.fm.cwd.write;
		}
		
		this.cm = function(t) {
			return t == 'cwd';
		}
	},
	
	/**
	 * @class Upload files
	 * @param Object  elFinder
	 **/
	upload : function(fm) {
		var self  = this;
		this.name = 'Upload files';
		this.fm   = fm;
		
		this.exec = function() {

			var id = 'el-finder-io-'+(new Date().getTime()),
				e = $('<div class="ui-state-error ui-corner-all"><span class="ui-icon ui-icon-alert"/><div/></div>'),
				m = this.fm.params.uplMaxSize ? '<p>'+this.fm.i18n('Maximum allowed files size')+': '+this.fm.params.uplMaxSize+'</p>' : '',
				b = $('<p class="el-finder-add-field"><span class="ui-state-default ui-corner-all"><em class="ui-icon ui-icon-circle-plus"/></span>'+this.fm.i18n('Add field')+'</p>')
					.click(function() { $(this).before('<p><input type="file" name="upload[]"/></p>'); }),
				f = '<form method="post" enctype="multipart/form-data" action="'+self.fm.options.url+'" target="'+id+'"><input type="hidden" name="cmd" value="upload" /><input type="hidden" name="current" value="'+self.fm.cwd.hash+'" /><input type="hidden" name="masterdc" value="'+self.fm.cwd.masterdc+'" /><input type="hidden" name="ownerid" value="'+self.fm.cwd.ownerid+'" />',
				d = $('<div/>'),
				i = 3;

				while (i--) { f += '<p><input type="file" name="upload[]"/></p>'; }

				// Rails csrf meta tag (for XSS protection), see #256
				var rails_csrf_token = $('meta[name=csrf-token]').attr('content');
				var rails_csrf_param = $('meta[name=csrf-param]').attr('content');
				if (rails_csrf_param != null && rails_csrf_token != null) {
					f += '<input name="'+rails_csrf_param+'" value="'+rails_csrf_token+'" type="hidden" />';
				}

				f = $(f+'</form>');
				
				d.append(f.append(e.hide()).prepend(m).append(b)).dialog({
						dialogClass : 'el-finder-dialog',
						title       : self.fm.i18n('Upload files'),
						modal       : true,
						resizable   : false,
						close       : function() { self.fm.lockShortcuts(); },
						width:450,
						buttons     : {
							'取消' : function() { $(this).dialog('close'); },
							'确定'     : function() {
								if (!$(':file[value]', f).length) {
									return error(self.fm.i18n('Select at least one file to upload'));
								}
								setTimeout(function() {
									var err;
									$('input[type=file]',f).each(function(i){
										var name = $(this).val();
										if(name==""){
											return true;
										}
										if (!self.fm.isValidName(name)) {
											err = 'Invalid name';
										} else if (self.fm.fileExists(name)) {
											err = 'File or folder with the same name already exists Whether covered';
										}										
										if (err=='File or folder with the same name already exists Whether covered') {
											$('<div><div class="ui-state-error ui-corner-all"><span class="ui-icon ui-icon-alert"/><strong>' + self.fm.i18n(err) + '</strong></div></div>')
	                                        .dialog({
	                                            title: '是否覆盖上传',
	                                            dialogClass: 'el-finder-dialog',
	                                            width: 350,
	                                            close: function () {
	                                                self.fm.lockShortcuts();
	                                            },
	                                            buttons: {
	                                                '取消': function () {
	                                                    $(this).dialog('close');
	                                                },
	                                                '确定': function () {
	                                                    $(this).dialog('close');	                                                   
	                                                    self.fm.lock();
	                                                    submit(true);
	                                                }
	                                            }
	                                        });
											return false;
										}
									});
									if (err) {
										return false;
									}
									
									self.fm.lock();
									if ($.browser.safari) {
										$.ajax({
											url     : self.fm.options.url,
											data    : {cmd : 'ping'},
											error   : submit,
											success : submit
										});
									} else {
										submit();
									}
								});
								$(this).dialog('close');
							}
						}
					});

			self.fm.lockShortcuts(true);

			function error(err) {
				e.show().find('div').empty().text(err);
			}

			function submit(convertfile) {
				var $io = $('<iframe name="'+id+'" name="'+id+'" src="about:blank"/>'),
					io  = $io[0],
					cnt = 50,
					doc, html, data;
					
				$io.css({ position: 'absolute', top: '-1000px', left: '-1000px' })
				.appendTo('body').bind('load', function() {
					$io.unbind('load');
					result();
				});
				
				self.fm.lock(true);
				if(convertfile){
					var nextUrl = f.attr("action")+"?force=true";
					f.attr("action", nextUrl); 
				}
				f.submit();
				
				function result() {
					try {
						doc = io.contentWindow ? io.contentWindow.document : io.contentDocument ? io.contentDocument : io.document;
						/* opera */
						if (doc.body == null || doc.body.innerHTML == '') {
							if (--cnt) {
								return setTimeout(result, 100);
							} else {
								complite();
								return self.fm.view.error('Unable to access iframe DOM after 50 tries');
							}
						}
						/* get server response */
						html = $(doc.body).html();
						if (self.fm.jquery>=141) {
							data = $.parseJSON(html);
						} else if ( /^[\],:{}\s]*$/.test(html.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, "@")
							.replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, "]")
							.replace(/(?:^|:|,)(?:\s*\[)+/g, "")) ) {
								/* get from jQuery 1.4 */
							data = window.JSON && window.JSON.parse ? window.JSON.parse(html) : (new Function("return " + html))();
						} else {
							data = { error : 'Unable to parse server response' };
						}
						
					} catch(e) {
						data = { error : 'Unable to parse server response' };
					}
					complite();
					data.error && self.fm.view.error(data.error, data.errorData);
					data.cwd && self.fm.reload(data);
					data.tmb && self.fm.tmb();
				}
				
				function complite() {
					self.fm.lock();
					$io.remove();
				}
					
			}
		}
		
		this.isAllowed = function() {
			return this.fm.cwd.write;
		}
		
		this.cm = function(t) {
			return t == 'cwd';
		}
	},
	
	/**
	 * @class Make file/folder copy
	 * @param Object  elFinder
	 **/
	duplicate : function(fm) {
		var self  = this;
		this.name = 'Duplicate';
		this.fm   = fm;
		
		this.exec = function() {
			this.fm.ajax({
				cmd     : 'duplicate',
				current : this.fm.cwd.hash,
				target  : this.fm.selected[0],
				masterdc : self.fm.cwd.masterdc, 
				ownerid : self.fm.cwd.ownerid
			},
			function(data) {
				self.fm.reload(data);
			});
		}
		
		this.isAllowed = function() {
			return this.fm.cwd.write && this.fm.selected.length == 1 && this.fm.getSelected()[0].read;
		}
		
		this.cm = function(t) {
			return t == 'file';
		}
	},
	
	/**
	 * @class Edit text file
	 * @param Object  elFinder
	 **/
	edit : function(fm) {
		var self  = this;
		this.name = 'Edit text file';
		this.fm   = fm;
		
		this.exec = function() {
			var f = this.fm.getSelected(0);
			this.fm.lockShortcuts(true);
			this.fm.ajax({
				cmd     : 'read',
				current : this.fm.cwd.hash,
				target    : f.hash,
				masterdc : self.fm.cwd.masterdc, 
				ownerid : self.fm.cwd.ownerid
			}, function(data) {
				self.fm.lockShortcuts(true);
				var ta = $('<textarea/>').val(data.content||'').keydown(function(e) {
					e.stopPropagation();
					if (e.keyCode == 9) {
						e.preventDefault();
						if ($.browser.msie) {
							var r = document.selection.createRange();
							r.text = "\t"+r.text;
							this.focus();
						} else {
							var before = this.value.substr(0, this.selectionStart),
							after = this.value.substr(this.selectionEnd);
							this.value = before+"\t"+after;
							this.setSelectionRange(before.length+1, before.length+1);
						}
					}
				});
				$('<div/>').append(ta)
					.dialog({
						dialogClass : 'el-finder-dialog',
						title   : self.fm.i18n(self.name),
						modal   : true,
						width   : 500,
						close   : function() { self.fm.lockShortcuts(); },
						buttons : {
							Cancel : function() { $(this).dialog('close'); },
							Ok     : function() {
								var c = ta.val();
								$(this).dialog('close');
								self.fm.ajax({
									cmd     : 'edit',
									current : self.fm.cwd.hash,
									target  : f.hash,
									content : c,
									masterdc : self.fm.cwd.masterdc, 
									ownerid : self.fm.cwd.ownerid
								}, function(data) {
									if (data.target) {
										self.fm.cdc[data.target.hash] = data.target;
										self.fm.view.updateFile(data.target);
										self.fm.selectById(data.target.hash);										
									}
								}, {type : 'POST'});
							}
						}
					});
			});
		}
		
		this.isAllowed = function() {
			if (self.fm.selected.length == 1) {
				var f = this.fm.getSelected()[0];
				return f.write && f.read && (f.mime.indexOf('text') == 0 || f.mime == 'application/x-empty' || f.mime == 'application/xml');
			}
		}
		
		this.cm = function(t) {
			return t == 'file';
		}
	},
	
	/**
	 * @class Create archive
	 * @param Object  elFinder
	 **/
	archive : function(fm) {
		var self  = this;
		this.name = 'Create archive';
		this.fm   = fm;
		
		this.exec = function(t) {
			var o = {
				cmd     : 'archive',
				current : self.fm.cwd.hash,
				type    : $.inArray(t, this.fm.params.archives) != -1 ? t : this.fm.params.archives[0],
				name    : self.fm.i18n('Archive'),
				masterdc : self.fm.cwd.masterdc, 
				ownerid : self.fm.cwd.ownerid
			};
			if (this.fm.jquery>132) {
				o.targets = self.fm.selected;
			} else {
				o['targets[]'] = self.fm.selected;
			}
			
			this.fm.ajax(o, function(data) { self.fm.reload(data); });
		}
		
		this.isAllowed = function() {
			if (this.fm.cwd.write && this.fm.selected.length) {
				var s = this.fm.getSelected(), l = s.length;
				while (l--) {
					if (s[l].read) {
						return true;
					}
				}
			}
			return false;
		}
		
		this.cm = function(t) {
			return t != 'cwd';
		}
		
		this.argc = function() {
			var i, v = [];
			for (i=0; i < self.fm.params.archives.length; i++) {
				v.push({
					'class' : 'archive',
					'argc'  : self.fm.params.archives[i],
					'text'  : self.fm.view.mime2kind(self.fm.params.archives[i])
				});
			};
			return v;
		}
	},
	
	/**
	 * @class Extract files from archive
	 * @param Object  elFinder
	 **/
	extract : function(fm) {
		var self  = this;
		this.name = 'Uncompress archive';
		this.fm   = fm;
		
		this.exec = function() {
			this.fm.ajax({
				cmd     : 'extract',
				current : this.fm.cwd.hash,
				target  : this.fm.getSelected(0).hash,
				masterdc : self.fm.cwd.masterdc, 
				ownerid : self.fm.cwd.ownerid
			}, function(data) {
				self.fm.reload(data);
			})
		}
		
		this.isAllowed = function() {
			var extract = this.fm.params.extract,
				cnt = extract && extract.length;
			return this.fm.cwd.write && this.fm.selected.length == 1 && this.fm.getSelected(0).read && cnt && $.inArray(this.fm.getSelected(0).mime, extract) != -1;
		}
		
		this.cm = function(t) {
			return t == 'file';
		}
	},
	
	/**
	 * @class Resize image
	 * @param Object  elFinder
	 **/
	resize : function(fm) {
		var self  = this;
		this.name = 'Resize image';
		this.fm   = fm;
		
		this.exec = function() {
			var s = this.fm.getSelected();
			if (s[0] && s[0].write && s[0].dim) {
				var size = s[0].dim.split('x'), 
					w  = parseInt(size[0]), 
					h  = parseInt(size[1]), rel = w/h
					iw = $('<input type="text" size="9" value="'+w+'" name="width"/>'),
					ih = $('<input type="text" size="9" value="'+h+'" name="height"/>'),
					f  = $('<form/>').append(iw).append(' x ').append(ih).append(' px');
				iw.add(ih).bind('change', calc);
				self.fm.lockShortcuts(true);
				var d = $('<div/>').append($('<div/>').text(self.fm.i18n('Dimensions')+':')).append(f).dialog({
					title : self.fm.i18n('Resize image'),
					dialogClass : 'el-finder-dialog',
					width : 230,
					modal : true,
					close : function() { self.fm.lockShortcuts(); },
					buttons : {
						Cancel : function() { $(this).dialog('close'); },
						Ok     : function() {
							var _w = parseInt(iw.val()) || 0,
								_h = parseInt(ih.val()) || 0;
							if (_w>0 && _w != w && _h>0 && _h != h) {
								self.fm.ajax({
									cmd     : 'resize',
									current : self.fm.cwd.hash,
									target  : s[0].hash,
									width   : _w,
									height  : _h
								},
								function (data) {
									self.fm.reload(data);
								});
							}
							$(this).dialog('close');
						}
					}
				});
			} 
			
			function calc() {
				var _w = parseInt(iw.val()) || 0,
					_h = parseInt(ih.val()) || 0;
					
				if (_w<=0 || _h<=0) {
					_w = w;
					_h = h;
				} else if (this == iw.get(0)) {
					_h = parseInt(_w/rel);
				} else {
					_w = parseInt(_h*rel);
				}
				iw.val(_w);
				ih.val(_h);
			}
			
		}
		
		this.isAllowed = function() {
			return this.fm.selected.length == 1 && this.fm.cdc[this.fm.selected[0]].write && this.fm.cdc[this.fm.selected[0]].read && this.fm.cdc[this.fm.selected[0]].resize;
		}
		
		this.cm = function(t) {
			return t == 'file';
		}
	},
	
	/**
	 * @class Switch elFinder into icon view
	 * @param Object  elFinder
	 **/
	icons : function(fm) {
		this.name = 'View as icons';
		this.fm   = fm;
		
		this.exec = function() {
			this.fm.view.win.addClass('el-finder-disabled');
			this.fm.setView('icons');
			this.fm.updateCwd();
			this.fm.view.win.removeClass('el-finder-disabled');
			$('div.image', this.fm.view.cwd).length && this.fm.tmb();
		}
		
		this.isAllowed = function() {
			return this.fm.options.view != 'icons';
		}
		
		this.cm = function(t) {
			return t == 'cwd';
		}
	},
	
	/**
	 * @class Switch elFinder into list view
	 * @param Object  elFinder
	 **/
	list : function(fm) {
		this.name = 'View as list';
		this.fm   = fm;
		
		this.exec = function() {
			this.fm.view.win.addClass('el-finder-disabled');
			this.fm.setView('list');
			this.fm.updateCwd();
			this.fm.view.win.removeClass('el-finder-disabled');
		}
		
		this.isAllowed = function() {
			return this.fm.options.view != 'list';
		}
		
		this.cm = function(t) {
			return t == 'cwd';
		}
	},
	
	help : function(fm) {
		this.name = 'Help';
		this.fm   = fm;
		
		this.exec = function() {
			var h, ht = this.fm.i18n('helpText'), a, s, tabs; 
			
			h = '<div class="el-finder-logo"/><strong>'+this.fm.i18n('elFinder: Web file manager')+'</strong><br/>'+this.fm.i18n('Version')+': '+this.fm.version+'<br/>'
				+'jQuery/jQueryUI: '+$().jquery+'/'+$.ui.version+'<br clear="all"/>'
				+'<p><strong><a href="http://elrte.org/'+this.fm.options.lang+'/elfinder" target="_blank">'+this.fm.i18n('Donate to support project development')+'</a></strong></p>'
				+ '<p><a href="http://elrte.org/redmine/projects/elfinder/wiki" target="_blank">'+this.fm.i18n('elFinder documentation')+'</a></p>';
			h += '<p>'+(ht != 'helpText' ? ht : 'elFinder works similar to file manager on your computer. <br /> To make actions on files/folders use icons on top panel. If icon action it is not clear for you, hold mouse cursor over it to see the hint. <br /> Manipulations with existing files/folders can be done through the context menu (mouse right-click).<br/> To copy/delete a group of files/folders, select them using Shift/Alt(Command) + mouse left-click.')+'</p>';
			h += '<p>'
				+ '<strong>'+this.fm.i18n('elFinder support following shortcuts')+':</strong><ul>'
				+ '<li><kbd>Ctrl+A</kbd> - '+this.fm.i18n('Select all files')+'</li>'
			 	+ '<li><kbd>Ctrl+C/Ctrl+X/Ctrl+V</kbd> - '+this.fm.i18n('Copy/Cut/Paste files')+'</li>'
			 	+ '<li><kbd>Enter</kbd> - '+this.fm.i18n('Open selected file/folder')+'</li>'
				+ '<li><kbd>Space</kbd> - '+this.fm.i18n('Open/close QuickLook window')+'</li>'
			 	+ '<li><kbd>Delete/Cmd+Backspace</kbd> - '+this.fm.i18n('Remove selected files')+'</li>'
			 	+ '<li><kbd>Ctrl+I</kbd> - '+this.fm.i18n('Selected files or current directory info')+'</li>'
			 	+ '<li><kbd>Ctrl+N</kbd> - '+this.fm.i18n('Create new directory')+'</li>'
			 	+ '<li><kbd>Ctrl+U</kbd> - '+this.fm.i18n('Open upload files form')+'</li>'
			 	+ '<li><kbd>Left arrow</kbd> - '+this.fm.i18n('Select previous file')+'</li>'
			 	+ '<li><kbd>Right arrow </kbd> - '+this.fm.i18n('Select next file')+'</li>'
			 	+ '<li><kbd>Ctrl+Right arrow</kbd> - '+this.fm.i18n('Open selected file/folder')+'</li>'
			 	+ '<li><kbd>Ctrl+Left arrow</kbd> - '+this.fm.i18n('Return into previous folder')+'</li>'
			 	+ '<li><kbd>Shift+arrows</kbd> - '+this.fm.i18n('Increase/decrease files selection')+'</li></ul>'
			 	+ '</p><p>'
			 	+ this.fm.i18n('Contacts us if you need help integrating elFinder in you products')+': dev@std42.ru</p>';

			a = '<div class="el-finder-help-std"/>'
				+'<p>'+this.fm.i18n('Javascripts/PHP programming: Dmitry (dio) Levashov, dio@std42.ru')+'</p>'
				+'<p>'+this.fm.i18n('Python programming, techsupport: Troex Nevelin, troex@fury.scancode.ru')+'</p>'
				+'<p>'+this.fm.i18n('Design: Valentin Razumnih')+'</p>'
				+'<p>'+this.fm.i18n('Chezh localization')+': Roman Matěna, info@romanmatena.cz</p>'
				+'<p>'+this.fm.i18n('Chinese (traditional) localization')+': Tad, tad0616@gmail.com</p>'
				+'<p>'+this.fm.i18n('Dutch localization')+': Kurt Aerts, <a href="http://ilabsolutions.net/" target="_blank">http://ilabsolutions.net</a></p>'
				+'<p>'+this.fm.i18n('Greek localization')+': Panagiotis Skarvelis</p>'
				+'<p>'+this.fm.i18n('Hungarian localization')+': Viktor Tamas, tamas.viktor@totalstudio.hu</p>'
				+'<p>'+this.fm.i18n('Italian localization')+':  Ugo Punzolo, sadraczerouno@gmail.com</p>'
				+'<p>'+this.fm.i18n('Latvian localization')+':  Uldis Plotiņš, uldis.plotins@gmail.com</p>'
				+'<p>'+this.fm.i18n('Poland localization')+':  Darek Wapiński, darek@wapinski.us</p>'
				+'<p>'+this.fm.i18n('Spanish localization')+': Alex (xand) Vavilin, xand@xand.es, <a href="http://xand.es" target="_blank">http://xand.es</a></p>'
				+'<p>'+this.fm.i18n('Icons')+': <a href="http://pixelmixer.ru/" target="_blank">pixelmixer</a>,  <a href="http://www.famfamfam.com/lab/icons/silk/" target="_blank">Famfam silk icons</a>, <a href="http://www.fatcow.com/free-icons/" target="_blank">Fatcow icons</a>'+'</p>'
				+'<p>'+this.fm.i18n('Copyright: <a href="http://www.std42.ru" target="_blank">Studio 42 LTD</a>')+'</p>'
				+'<p>'+this.fm.i18n('License: BSD License')+'</p>'
				+'<p>'+this.fm.i18n('Web site: <a href="http://elrte.org/elfinder/" target="_blank">elrte.org/elfinder</a>')+'</p>';
			
			s = '<div class="el-finder-logo"/><strong><a href="http://www.eldorado-cms.ru" target="_blank">ELDORADO.CMS</a></strong><br/>'
				+this.fm.i18n('Simple and usefull Content Management System')
				+'<hr/>'
				+ this.fm.i18n('Support project development and we will place here info about you');
			
			tabs = '<ul><li><a href="#el-finder-help-h">'+this.fm.i18n('Help')+'</a></li><li><a href="#el-finder-help-a">'+this.fm.i18n('Authors')+'</a><li><a href="#el-finder-help-sp">'+this.fm.i18n('Sponsors')+'</a></li></ul>'
					+'<div id="el-finder-help-h"><p>'+h+'</p></div>'
					+'<div id="el-finder-help-a"><p>'+a+'</p></div>'
					+'<div id="el-finder-help-sp"><p>'+s+'</p></div>';
			
			var d = $('<div/>').html(tabs).dialog({
				width       : 617,
				title       : this.fm.i18n('Help'),
				dialogClass : 'el-finder-dialog',
				modal       : true,
				close       : function() { d.tabs('destroy').dialog('destroy').remove() },
				buttons     : {
					Ok : function() { $(this).dialog('close'); }
				}
			}).tabs()
		}


		this.cm = function(t) {
			return t == 'cwd';
		}
	}
}

})(jQuery);

/**
 * @class Create quick look window (similar to MacOS X Quick Look)
 * @author dio dio@std42.ru
 **/
(function($) {
elFinder.prototype.quickLook = function(fm, el) {
	var self    = this;
	this.fm     = fm;
	this._hash  = '';
	this.title  = $('<strong/>');
	this.ico    = $('<p/>');
	this.info   = $('<label/>');
	this.media  = $('<div class="el-finder-ql-media"/>').hide()
	
	this.name = $('<span class="el-finder-ql-name"/>')
	this.kind = $('<span class="el-finder-ql-kind"/>')
	this.size = $('<span class="el-finder-ql-size"/>')
	this.date = $('<span class="el-finder-ql-date"/>')
	this.url  = $('<a href="#"/>').hide().click(function(e) {
		e.preventDefault();
		window.open($(this).attr('href'));
		self.hide();
	});

	this.add = $('<div/>')
	this.content = $('<div class="el-finder-ql-content"/>')
	this.win     = $('<div class="el-finder-ql"/>').hide()
		.append($('<div class="el-finder-ql-drag-handle"/>').append($('<span class="ui-icon ui-icon-circle-close"/>').click(function() { self.hide(); })).append(this.title))
		.append(this.ico)
		.append(this.media)
		.append(this.content.append(this.name).append(this.kind).append(this.size).append(this.date).append(this.url).append(this.add))
		// .appendTo(this.fm.view.win)
		.appendTo('body')
		.draggable({handle : '.el-finder-ql-drag-handle'})
		.resizable({
			minWidth  : 420,
			minHeight : 150,
			resize    : function() {
				if (self.media.children().length) {
					var t = self.media.children(':first');
					switch (t[0].nodeName) {
						case 'IMG':
							var w = t.width(),
								h = t.height(),
								_w = self.win.width(),
								_h = self.win.css('height') == 'auto' ? 350 : self.win.height()-self.content.height()-self.th,
								r = w>_w || h>_h 
									? Math.min(Math.min(_w, w)/w, Math.min(_h, h)/h) 
									: Math.min(Math.max(_w, w)/w, Math.max(_h, h)/h);
							t.css({
								width : Math.round(t.width()*r),
								height : Math.round(t.height()*r)
							})
							break;
						case 'IFRAME':
						case 'EMBED':
							t.css('height', self.win.height()-self.content.height()-self.th);
							break;
						case 'OBJECT':
							t.children('embed').css('height', self.win.height()-self.content.height()-self.th);
					}
				}
			}
		});
	
	this.th = parseInt(this.win.children(':first').css('height'))||18;
	/* All browsers do it, but some is shy to says about it. baka da ne! */
	this.mimes = {
		'image/jpeg' : 'jpg',
		'image/gif'  : 'gif',
		'image/png'  : 'png'
		};

	for (var i=0; i<navigator.mimeTypes.length; i++) {
		var t = navigator.mimeTypes[i].type;
		if (t && t != '*') {
			this.mimes[t] = navigator.mimeTypes[i].suffixes;
		}
	}
	
	if (($.browser.safari && navigator.platform.indexOf('Mac') != -1) || $.browser.msie) {
		/* not booletproof, but better then nothing */
		this.mimes['application/pdf'] = 'pdf';
	} else {
		for (var n=0; n < navigator.plugins.length; n++) {
			for (var m=0; m < navigator.plugins[n].length; m++) {
				var e = navigator.plugins[n][m].description.toLowerCase();
				if (e.substring(0, e.indexOf(" ")) == 'pdf') {
					this.mimes['application/pdf'] = 'pdf';
					break;
				}
			}
		}
	}
	
	if (this.mimes['image/x-bmp']) {
		this.mimes['image/x-ms-bmp'] = 'bmp';
	}
	
	if ($.browser.msie && !this.mimes['application/x-shockwave-flash']) {
		this.mimes['application/x-shockwave-flash'] = 'swf';
	}
	
	// self.fm.log(this.mimes)
	
	/**
	 * Open quickLook window
	 **/
	this.show = function() {
		if (this.win.is(':hidden') && self.fm.selected.length == 1) {
			update();
			var id = self.fm.selected[0],
			 	el = self.fm.view.cwd.find('[key="'+id+'"]'),
				o  = el.offset();
				
			self.fm.lockShortcuts(true);
			this.win.css({
				width    : el.width()-20,
				height   : el.height(),
				left     : o.left,
				top      : o.top,
				opacity  : 0
			}).show().animate({
				width    : 420,
				height   : 150,
				opacity  : 1,
				top      : Math.round($(window).height()/5),
				left     : $(window).width()/2-210
			}, 450, function() { 
				self.win.css({height: 'auto'});
				self.fm.lockShortcuts();
			});
		}
	}
	
	/**
	 * Close quickLook window
	 **/
	this.hide = function() {
		if (this.win.is(':visible')) {
			var o, el = self.fm.view.cwd.find('[key="'+this._hash+'"]');
			if (el) {
				o = el.offset();
				this.media.hide(200)//.empty()
				this.win.animate({
					width    : el.width()-20,
					height   : el.height(),
					left     : o.left,
					top      : o.top,
					opacity  : 0
				}, 350, function() {
					self.fm.lockShortcuts();
					reset();
					self.win.hide().css('height', 'auto');
				});
			} else {
				this.win.fadeOut(200);
				reset();
				self.fm.lockShortcuts();
			}
		}
	}
	
	/**
	 * Open/close quickLook window
	 **/
	this.toggle = function() {
		if (this.win.is(':visible')) {
			this.hide();
		} else {
			this.show();
		}
	}
	
	/**
	 * Update quickLook window content if only one file selected,
	 * otherwise close window
	 **/
	this.update = function() {
		if (this.fm.selected.length != 1) {
			this.hide();
		} else if (this.win.is(':visible') && this.fm.selected[0] != this._hash) {
			update();
		}
	}
	
	/**
	 * Return height of this.media block
	 * @return Number
	 **/
	this.mediaHeight = function() {
		return this.win.is(':animated') || this.win.css('height') == 'auto' ? 315 : this.win.height()-this.content.height()-this.th;
	}
	
	/**
	 * Clean quickLook window DOM elements
	 **/
	function reset() {
		self.media.hide().empty();
		self.win.attr('class', 'el-finder-ql').css('z-index', self.fm.zIndex);
		self.title.empty();
		self.ico.attr('style', '').show();
		self.add.hide().empty();
		self._hash = '';
	}
	
	/**
	 * Update quickLook window content
	 **/
	function update() {
		var f = self.fm.getSelected(0);
		reset();

		self._hash = f.hash;
		self.title.text(f.name);
		self.win.addClass(self.fm.view.mime2class(f.mime));
		self.name.text(f.name);
		self.kind.text(self.fm.view.mime2kind(f.link ? 'symlink' : f.mime)); 
		self.size.text(self.fm.view.formatSize(f.size));
		self.date.text(self.fm.i18n('Modified')+': '+self.fm.view.formatDate(f.date));
		f.dim && self.add.append('<span>'+f.dim+' px</span>').show();
		f.tmb && self.ico.css('background', 'url("'+f.tmb+'") 0 0 no-repeat');
		if (f.url) {
			self.url.text(f.url).attr('href', f.url).show();
			for (var i in self.plugins) {
				if (self.plugins[i].test && self.plugins[i].test(f.mime, self.mimes, f.name)) {
					self.plugins[i].show(self, f);
					return;
				}
			}
		} else {
			self.url.hide();
		}
		
		self.win.css({
			width  : '420px',
			height : 'auto'
		});
	}

}

elFinder.prototype.quickLook.prototype.plugins = {
	
	image : new function() {

		this.test = function(mime, mimes) {
			return mime.match(/^image\//);
		}
		
		this.show = function(ql, f) {
			var url, t;

			if (ql.mimes[f.mime] && f.hash == ql._hash) {
				$('<img/>').hide().appendTo(ql.media.show()).attr('src', f.url+($.browser.msie || $.browser.opera ? '?'+Math.random() : '')).load(function() {
					t = $(this).unbind('load');
					if (f.hash == ql._hash) { 
						ql.win.is(':animated') ? setTimeout(function() { preview(t); }, 330) : preview(t);
					} 
				});
			}
			
			function preview(img) {
				var w = img.width(),
					h = img.height(),
					a = ql.win.is(':animated'),
					_w = a ? 420 : ql.win.width(), 
					_h = a || ql.win.css('height') == 'auto' ? 315 : ql.win.height()-ql.content.height()-ql.th,
					r = w>_w || h>_h 
						? Math.min(Math.min(_w, w)/w, Math.min(_h, h)/h)
						: Math.min(Math.max(_w, w)/w, Math.max(_h, h)/h);

				ql.fm.lockShortcuts(true);
				ql.ico.hide();
				img.css({
					width  : ql.ico.width(),
					height : ql.ico.height()
				}).show().animate({
					width  : Math.round(r*w),
					height : Math.round(r*h)
				}, 450, function() { 
					ql.fm.lockShortcuts(); 
				});
			}
		}
		
	},
	
	text : new function() {

		this.test = function(mime, mimes) {
			return (mime.indexOf('text') == 0 && mime.indexOf('rtf') == -1) || mime.match(/application\/(xml|javascript|json)/);
		}
		
		this.show = function(ql, f) {
			if (f.hash == ql._hash) {
				ql.ico.hide();
				ql.media.append('<iframe src="'+f.url+'" style="height:'+ql.mediaHeight()+'px" />').show();
			}
		}
	},
	
	swf : new function() {
		
		this.test = function(mime, mimes) {
			return mime == 'application/x-shockwave-flash' && mimes[mime];
		}
		
		this.show = function(ql, f) {
			if (f.hash == ql._hash) {
				ql.ico.hide();
				// ql.media.append('<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,40,0"><param name="quality" value="high" /><param name="movie" value="'+url+'" /><embed pluginspage="http://www.macromedia.com/go/getflashplayer" quality="high" src="'+url+'" type="application/x-shockwave-flash" style="width:100%;height:'+ql.mediaHeight()+'px"></embed></object>')
					// .slideDown(400);
				var e = ql.media.append('<embed pluginspage="http://www.macromedia.com/go/getflashplayer" quality="high" src="'+f.url+'" style="width:100%;height:'+ql.mediaHeight()+'px" type="application/x-shockwave-flash" />'); 
				if (ql.win.is(':animated')) {
					e.slideDown(450)
				} else {
					e.show()
				}
			}
		}
	},
	
	audio : new function() {
		
		this.test = function(mime, mimes) {
			return mime.indexOf('audio') == 0 && mimes[mime];
		}
		
		this.show = function(ql, f) {
			if (f.hash == ql._hash) {
				ql.ico.hide();
				var h = ql.win.is(':animated') || ql.win.css('height') == 'auto' ? 100 : ql.win.height()-ql.content.height()-ql.th;
				ql.media.append('<embed src="'+f.url+'" style="width:100%;height:'+h+'px" />').show();
			}
		}
	},
	
	video : new function() {
		
		this.test = function(mime, mimes) {
			return mime.indexOf('video') == 0 && mimes[mime];
		}
		
		this.show = function(ql, f) {
			if (f.hash == ql._hash) {
				ql.ico.hide();
				ql.media.append('<embed src="'+f.url+'" style="width:100%;height:'+ql.mediaHeight()+'px" />').show();
			}
		}
		
	},
	
	pdf : new function() {
		
		this.test = function(mime, mimes) {
			return mime == 'application/pdf' && mimes[mime];
		}
		
		this.show = function(ql, f) {
			if (f.hash == ql._hash) {
				ql.ico.hide();
				ql.media.append('<embed src="'+f.url+'" style="width:100%;height:'+ql.mediaHeight()+'px" />').show();
			}
		}
	}
	
}


})(jQuery);
/**
 * @class  Bind/update events
 * @author dio dio@std42.ru
 **/
(function($) {
elFinder.prototype.eventsManager = function(fm, el) {
	var self   = this;
	this.lock  = false;
	this.fm    = fm;
	this.ui    = fm.ui;
	this.tree  = fm.view.tree
	this.cwd   = fm.view.cwd;
	this.pointer = '';

	/**
	 * Initial events binding
	 *
	 **/
	this.init = function() {
		var self = this, ignore = false;
		
		self.lock = false;
		$(window).resize(function(){			
			var w = $(document).width();
			var h = $(document).height()-50;
			w && self.fm.view.win.width(w);
			h && self.fm.view.nav.height(h);
			h && self.fm.view.cwd.height(h-95);
		});
		this.cwd
			.bind('click', function(e) {
				var t = $(e.target);
				if (t.hasClass('ui-selected')) {
					self.fm.unselectAll();
				} else {
					if (!t.attr('key')) {
						t = t.parent('[key]');
					}
					if (e.ctrlKey || e.metaKey) {
						self.fm.toggleSelect(t);
					} else {
						self.fm.select(t, true);
					}
				}
			})
			.bind(window.opera?'click':'contextmenu', function(e) {
				if (window.opera && !e.ctrlKey) {
					return;
				}
				e.preventDefault();
				e.stopPropagation();

				var t = $(e.target);
				if ($.browser.mozilla) {
					ignore = true;
				}
				

				if (t.hasClass('el-finder-cwd')) {
					self.fm.unselectAll();
				} else {
					self.fm.select(t.attr('key') ? t : t.parent('[key]'));
				}
				self.fm.quickLook.hide();
				self.fm.ui.showMenu(e);
			})
			.selectable({
				filter : '[key]',
				delay  : 300,
				stop   : function() { self.fm.updateSelect(); self.fm.log('mouseup') }
			});
		if (window.attachEvent) {
			this.cwd[0].attachEvent('dragenter', function(e) {
				e.preventDefault();
				e.stopPropagation();					
			}, false);

			this.cwd[0].attachEvent('dragleave', function(e) {
				e.preventDefault();
				e.stopPropagation();
			}, false);

			this.cwd[0].attachEvent('dragover', function(e) {
				e.preventDefault();
				e.stopPropagation();
				
			}, false);

			this.cwd[0].attachEvent('drop', function(e) {
			  	e.preventDefault();		  	
				e.dataTransfer && e.dataTransfer.files &&  e.dataTransfer.files.length && fm.upload({files : e.dataTransfer.files})//fm.upload({files : e.dataTransfer.files});
			}, false);
			if(typeof(this.fm.view.activex)!=='undefined'){
				this.fm.view.activex[0].attachEvent('Upload', function(path) {
					function sendfile(tempfile,offsize,convertfile){
						var sendurl = self.fm.options.url;
						if(convertfile){
							sendurl = sendurl+"?force=true";
						}
						xhr1.open("POST", sendurl, true);
						var endindex = (offsize+stepsize)>filesize?filesize:(offsize+stepsize);
						var contentRange = offsize.toString()+'-'+(endindex-1).toString()+'/'+filesize.toString();
						var body = '';
						var boundary = '---------------------------';
						boundary += Math.floor(Math.random() * 32768);
						boundary += Math.floor(Math.random() * 32768);
						boundary += Math.floor(Math.random() * 32768);
						xhr1.setRequestHeader("Content-Type", 'multipart/form-data; boundary=' + boundary);
						body += '--' + boundary + '\r\n' + 'Content-Disposition: form-data; name="';
						body += 'cmd"';
						body += '\r\n\r\n';
						body += 'upload';
						body += '\r\n'
						
						body += '--' + boundary + '\r\n' + 'Content-Disposition: form-data; name="';
						body += 'current"';
						body += '\r\n\r\n';
						body += self.fm.cwd.hash;
						body += '\r\n'
							
						body += '--' + boundary + '\r\n' + 'Content-Disposition: form-data; name="';
						body += 'masterdc"';
						body += '\r\n\r\n';
						body += self.fm.cwd.masterdc;
						body += '\r\n'
							
						body += '--' + boundary + '\r\n' + 'Content-Disposition: form-data; name="';
						body += 'ownerid"';
						body += '\r\n\r\n';
						body += self.fm.cwd.ownerid;
						body += '\r\n'
							
						body += '--' + boundary + '\r\n' + 'Content-Disposition: form-data; name="';
						body += 'type"';
						body += '\r\n\r\n';
						body += 'activex';
						body += '\r\n'
							
						body += '--' + boundary + '\r\n' + 'Content-Disposition: form-data; name="';
						body += 'ContentRange"';
						body += '\r\n\r\n';
						body += contentRange;
						body += '\r\n'
						
						body += '--' + boundary + '\r\n' + 'Content-Disposition: form-data; name="';
						body += 'upload[]"';
						body += ';filename="'+filename+'"';
						body += '\r\n'
						body += 'Content-Type: text/plain';
						body += '\r\n\r\n';
						body += tempfile;
						body += '\r\n'
						body += '--' + boundary + '--';					
						xhr1.setRequestHeader('Content-length', body.length);					
						xhr1.onreadystatechange=function() // 状态改变回调函数
						{	
							if (xhr1.readyState == 0) { //尚未初始化0					    
							} else if (xhr1.readyState == 1) { //正在加载数据.....					    
							} else if (xhr1.readyState == 2) { //加载完毕					    
							} else if (xhr1.readyState == 3) { //正在处理					    
							} else if (xhr1.readyState == 4) { //处理完毕					    
							    if (xhr1.status == 200) {
	//						        self.fm.lock(false);
							        data = self.fm.uploads.parseUploadData(xhr1.responseText);
							        if(parseInt(data.offsize)>=filesize){
							        	self.fm.lock(false);	
							        	self.fm.ui.fm.reload(data);					        	
							        }else{
							        	tempfile=actobj.ReadFileBase64(path,data.offsize,stepsize);
							        	sendfile(tempfile,data.offsize);					        	
							        }	        
							    }
							}
						}
						xhr1.send(body);
					};				
	//				while (filesize>offsize) {
	//					if((filesize-offsize)>stepsize){
	//						addsize = stepsize;
	//					}else{
	//						addsize = (filesize-offsize);
	//					}					
	//					if(typeof(tempfile)!=='undefined'){
	//						tempfile+=actobj.ReadFileBase64(path,offsize,addsize);
	//					}else{
	//						tempfile=actobj.ReadFileBase64(path,offsize,addsize);
	//					}
	//					offsize+=addsize;
	//				}
					var xhr1,err; 
					try{
						xhr1=new ActiveXObject("Msxml2.XMLHTTP");
					}catch(e){
						xhr1=new ActiveXObjec("Microsoft.XMLHTTP");
					}if(!xhr1 && typeof XMLHttpRequest!='undefined'){
						try{
							xhr1=new XMLHttpRequest();
						}catch(e){
							xhr1=false;
						}
					} 																		
					var pos=path.lastIndexOf('\\');
					var filename = path.substring(pos+1); 				
					var actobj = new ActiveXObject('LDUPLOAD.lduploadCtrl.1');
					var filesize = actobj.FileSize(path);		
					var tempfile;	
					var stepsize = 1024*16*36;
					var offsize = 0;
					self.fm.lock(true);				
					tempfile=actobj.ReadFileBase64(path,offsize,stepsize);	
					
					if (!self.fm.isValidName(filename)) {
					    err = 'Invalid name';
					} else if (self.fm.fileExists(filename)) {
					    err = 'File or folder with the same name already exists Whether covered';
					}
					if (err == 'File or folder with the same name already exists Whether covered') {
					    $('<div><div class="ui-state-error ui-corner-all"><span class="ui-icon ui-icon-alert"/><strong>' + self.fm.i18n(err) + '</strong></div></div>')
					        .dialog({
					            title: '是否覆盖上传',
					            dialogClass: 'el-finder-dialog',
					            width: 350,
					            close: function () {
					                self.fm.lockShortcuts();
					            },
					            buttons: {
					                '取消': function () {
					                    $(this).dialog('close');
					                },
					                '确定': function () {
					                    $(this).dialog('close');
					                    self.fm.lock();
					                    sendfile(tempfile,offsize,true);
					                }
					            }
					        });
					    return false;
					}
					
					sendfile(tempfile,offsize);
					
				}, false);
			}
		}else if(window.addEventListener){
			this.cwd[0].addEventListener('dragenter', function(e) {
				e.preventDefault();
				e.stopPropagation();					
			}, false);

			this.cwd[0].addEventListener('dragleave', function(e) {
				e.preventDefault();
				e.stopPropagation();
			}, false);

			this.cwd[0].addEventListener('dragover', function(e) {
				e.preventDefault();
				e.stopPropagation();
				
			}, false);

			this.cwd[0].addEventListener('drop', function(e) {
			  	e.preventDefault();	
			  	var err;
			  	if(e.dataTransfer && e.dataTransfer.files &&  e.dataTransfer.files.length){
			  		$.each(e.dataTransfer.files,function(i){
			  			var name = e.dataTransfer.files[i].name;			  			
			  			if (name == "") {
					  	    return true;
					  	}
					  	if (!self.fm.isValidName(name)) {
					  	    err = 'Invalid name';
					  	} else if (self.fm.fileExists(name)) {
					  	    err = 'File or folder with the same name already exists Whether covered';
					  	}
					  	if (err == 'File or folder with the same name already exists Whether covered') {
					  	    $('<div><div class="ui-state-error ui-corner-all"><span class="ui-icon ui-icon-alert"/><strong>' + self.fm.i18n(err) + '</strong></div></div>')
					  	        .dialog({
					  	            title: '是否覆盖上传',
					  	            dialogClass: 'el-finder-dialog',
					  	            width: 350,
					  	            close: function () {
					  	                self.fm.lockShortcuts();
					  	            },
					  	            buttons: {
					  	                '取消': function () {
					  	                    $(this).dialog('close');
					  	                },
					  	                '确定': function () {
					  	                    $(this).dialog('close');
					  	                    self.fm.lock();
					  	                    fm.upload({files : e.dataTransfer.files},true);
					  	                }
					  	            }
					  	        });
					  	    return false;
					  	}
			  		});
			  	}
			  	if (err) {
					return false;
				}
//			  	var name = $(this).val();
			  	
			  	
			  	
				e.dataTransfer && e.dataTransfer.files &&  e.dataTransfer.files.length && fm.upload({files : e.dataTransfer.files})//fm.upload({files : e.dataTransfer.files});
			}, false);
		}
		$('a.header-nav-link', self.fm.view.userinfo).click(function(e) {
			e.preventDefault();
			var t = $(this);
			if (!t.parent().hasClass("active")) {
				var o = {
						cmd       : 'userinfo'
					};
				self.fm.ajax(o, function(data) {
					$('.subheader-used-text',self.fm.view.userinfo).html('已使用: '+data.used);
					$('.subheader-total-text',self.fm.view.userinfo).html('总容量: '+data.totle);
					$('.quota_graph_bar',self.fm.view.userinfo).css("width",data.percent);
				}, {force : true});
			}
			t.parent().toggleClass("active");
		});
			
		$(document).bind('click', function(e) {
			!ignore && self.fm.ui.hideMenu(); 
			ignore = false
			$('input', self.cwd).trigger('change'); 

			if (!$(e.target).is('input,textarea,select')) {
				$('input,textarea').blur();
			}			
		    if($(e.target).closest("#inner-page-header a.header-nav-link").length == 0){
		    	var li = $("#inner-page-header .top-level-nav-item");
			    if (li.hasClass("active")) {
				   li.toggleClass("active");
			    }
		    }
		});

		$('input,textarea').live('focus', function(e) {
			self.lock = true;
		}).live('blur', function(e) {
			self.lock = false;
		});

		/* open parents dir in tree */
		this.tree.bind('select', function(e) {
			self.tree.find('a').removeClass('selected');
			$(e.target).addClass('selected').parents('li:has(ul)').children('ul').show().prev().children('div').addClass('expanded');
		});
		
		/* make places droppable */
		if (this.fm.options.places) {

			this.fm.view.plc.click(function(e) {
				e.preventDefault();
				var t = $(e.target),
					h = t.attr('key'), ul;
				
				if (h) {
					h != self.fm.cwd.hash && self.ui.exec('open', e.target)
				} else if (e.target.nodeName == 'A' || e.target.nodeName == 'DIV') {
					ul = self.fm.view.plc.find('ul');
					if (ul.children().length) {
						ul.toggle(300);
						self.fm.view.plc.children('li').find('div').toggleClass('expanded');
					}
				}
			});
			
			this.fm.view.plc.droppable({
				accept    : '(div,tr).directory',
				tolerance : 'pointer',
				over      : function() { $(this).addClass('el-finder-droppable'); },
				out       : function() { $(this).removeClass('el-finder-droppable'); },
				drop      : function(e, ui) {
					$(this).removeClass('el-finder-droppable');
					var upd = false;
					/* accept only folders with read access */
					ui.helper.children('.directory:not(.noaccess,.dropbox)').each(function() {
						if (self.fm.addPlace($(this).attr('key'))) {
							upd = true;
							$(this).hide();
						}
					});
					/* update places id's and view */
					if (upd) {
						self.fm.view.renderPlaces();
						self.updatePlaces();
						self.fm.view.plc.children('li').children('div').trigger('click');
					}
					/* hide helper if empty */
					if (!ui.helper.children('div:visible').length) {
						ui.helper.hide();
					}
				}
			});
		}
		
		/* bind shortcuts */
		
		$(document).bind($.browser.mozilla || $.browser.opera ? 'keypress' : 'keydown', function(e) {
			var meta = e.ctrlKey||e.metaKey;
			
			if (self.lock) {
				return;
			}
			switch(e.keyCode) {
				/* arrows left/up. with Ctrl - exec "back", w/o - move selection */
				case 37:
				case 38:
					e.stopPropagation();
					e.preventDefault();
					if (e.keyCode == 37 && meta) {
						self.ui.execIfAllowed('back');
					} else {
						moveSelection(false, !e.shiftKey);
					}
					break;
				/* arrows right/down. with Ctrl - exec "open", w/o - move selection  */
				case 39:
				case 40:
					e.stopPropagation();
					e.preventDefault();
					if (meta) {
						self.ui.execIfAllowed('open');
					} else {
						moveSelection(true, !e.shiftKey);
					}
					break;
			}
		});
		

		$(document).bind($.browser.opera ? 'keypress' : 'keydown', function(e) {

			if (self.lock) {
				return;
			}
			switch(e.keyCode) {
				/* Space - QuickLook */
				case 32:
					e.preventDefault();
					e.stopPropagation();
					self.fm.quickLook.toggle();
					break;
				/* Esc */	
				case 27:
					self.fm.quickLook.hide();
					break;
			}
		});
		
		if (!this.fm.options.disableShortcuts) {
			
			$(document).bind('keydown', function(e) {
				var meta = e.ctrlKey||e.metaKey;

				if (self.lock) {
					return;
				}
				switch (e.keyCode) {
					/* Meta+Backspace - delete */
					case 8:
						if (meta && self.ui.isCmdAllowed('rm')) {
							e.preventDefault();
							self.ui.exec('rm');
						} 
						break;
					/* Enter - exec "select" command if enabled, otherwise exec "open" */	
					case 13:
						if (self.ui.isCmdAllowed('select')) {
							return self.ui.exec('select');
						}
						self.ui.execIfAllowed('open');
						break;
					/* Delete */
					case 46:
						self.ui.execIfAllowed('rm');
						break;
					/* Ctrl+A */
					case 65:
						if (meta) {
							e.preventDefault();
							self.fm.selectAll();
						}
						break;
					/* Ctrl+C */
					case 67:
						meta && self.ui.execIfAllowed('copy');
						break;
					/* Ctrl+I - get info */	
					case 73:
						if (meta) {
							e.preventDefault();
							self.ui.exec('info');
						}
						break;
					/* Ctrl+N - new folder */
					case 78:
						if (meta) {
							e.preventDefault();
							self.ui.execIfAllowed('mkdir');
						}
						break;
					/* Ctrl+U - upload files */
					case 85:
					
						if (meta) {
							e.preventDefault();
							self.ui.execIfAllowed('upload');
						}
						break;
					/* Ctrl+V */
					case 86:
						meta && self.ui.execIfAllowed('paste');
						break;
					/* Ctrl+X */
					case 88:
						meta && self.ui.execIfAllowed('cut');
						break;
						
					case 113:
						self.ui.execIfAllowed('rename');
						break;
						
				}

			});
			
		}
	}
		
	/**
	 * Update browse
	 *
	 **/
	this.updateBrowse = function() {
		$('a', this.fm.view.browse).click(function(e) {
			e.preventDefault();
			var t = $(this), c;
			if (t.attr('key') != self.fm.cwd.hash) {
				if (t.hasClass('noaccess') || t.hasClass('dropbox')) {
					self.fm.view.error('Access denied');
				} else {
					self.ui.exec('open', t.trigger('select')[0]);
				}
			}
		});
	}
	/**
	 * Update ldwstree
	 *
	 **/
	this.updateldwsNav = function() {
		$('#main-nav a', this.fm.view.ldwstree).click(function(e) {
			e.preventDefault();
			var t = $(this), c;
			if (t.attr('key') != self.fm.cwd.hash) {
				if (t.hasClass('noaccess') || t.hasClass('dropbox')) {
					self.fm.view.error('Access denied');
				} else {
					$('.selected',t.parent().parent()).toggleClass("selected");
					t.toggleClass("selected");
					self.ui.exec('open', t.trigger('select')[0]);
				}
			}
		});				
	}
	/**
	 * Update navigation droppable/draggable
	 *
	 **/
	this.updateNav = function() {
		$('a', this.tree).click(function(e) {
			e.preventDefault();
			var t = $(this), c;
			if (e.target.nodeName == 'DIV' && $(e.target).hasClass('collapsed')) {
				$(e.target).toggleClass('expanded').parent().next('ul').toggle(300);
			} else if (t.attr('key') != self.fm.cwd.hash) {
				if (t.hasClass('noaccess') || t.hasClass('dropbox')) {
					self.fm.view.error('Access denied');
				} else {
					self.ui.exec('open', t.trigger('select')[0]);
				}
			} else {
				c = t.children('.collapsed');
				if (c.length) {
					c.toggleClass('expanded');
					t.next('ul').toggle(300);
				}
			}
		});
		
		$('a:not(.noaccess,.readonly)', this.tree).droppable({
			tolerance : 'pointer',
			accept : 'div[key],tr[key]',
			over   : function() { $(this).addClass('el-finder-droppable'); },
			out    : function() { $(this).removeClass('el-finder-droppable'); },
			drop   : function(e, ui) { $(this).removeClass('el-finder-droppable'); self.fm.drop(e, ui, $(this).attr('key')); }
		});
		this.fm.options.places && this.updatePlaces();
	}
	
	/**
	 * Update places draggable
	 *
	 **/
	this.updatePlaces = function() {
		this.fm.view.plc.children('li').find('li').draggable({
			scroll : false,
			stop   : function() {
				if (self.fm.removePlace($(this).children('a').attr('key'))) {
					$(this).remove();
					if (!$('li', self.fm.view.plc.children('li')).length) {
						self.fm.view.plc.children('li').find('div').removeClass('collapsed expanded').end().children('ul').hide();
					}
				}
			}
		});
	}
	
	/**
	 * Update folders droppable & files/folders draggable
	 **/
	this.updateCwd = function() {
		
		$('[key]', this.cwd)
			.bind('dblclick', function(e) {
				self.fm.select($(this), true);
				self.ui.exec(self.ui.isCmdAllowed('select') ? 'select' : 'open');
			})
			.draggable({
				delay      : 3,
				addClasses : false,
				appendTo : '.el-finder-cwd',
				revert     : true,
				drag       : function(e, ui) {
					ui.helper.toggleClass('el-finder-drag-copy', e.shiftKey||e.ctrlKey);
				},
				helper     : function() {
					var t = $(this),
						h = $('<div class="el-finder-drag-helper"/>'),
						c = 0;
					!t.hasClass('ui-selected') && self.fm.select(t, true);

					self.cwd.find('.ui-selected').each(function(i) {
						var el = self.fm.options.view == 'icons' ? $(this).clone().removeClass('ui-selected') : $(self.fm.view.renderIcon(self.fm.cdc[$(this).attr('key')]))
						if (c++ == 0 || c%12 == 0) {
							el.css('margin-left', 0);
						}
						h.append(el);
					});
					return h.css('width', (c<=12 ? 85+(c-1)*29 : 387)+'px');
				}
			})
			.filter('.directory')
			.droppable({
				tolerance : 'pointer',
				accept    : 'div[key],tr[key]',
				over      : function() { $(this).addClass('el-finder-droppable');  },
				out       : function() { $(this).removeClass('el-finder-droppable'); },
				drop      : function(e, ui) { 
					$(this).removeClass('el-finder-droppable');
					self.fm.drop(e, ui, $(this).attr('key')); }
			});
			
		if ($.browser.msie) {
			$('*', this.cwd).attr('unselectable', 'on')
				.filter('[key]')
					.bind('dragstart', function() { self.cwd.selectable('disable').removeClass('ui-state-disabled ui-selectable-disabled'); })
					.bind('dragstop', function() { self.cwd.selectable('enable'); });
		}
		$('a.shmodel-file', this.cwd).bind('click', function(e) {			
			var trkey = $(this).parent().parent().attr('key');
			var t = fm.cdc[trkey];
			var f = $('<form method="post" action="' + self.fm.options.url + '"><input type="hidden" name="cmd" value="getlink" /><input type="hidden" name="current" value="' + self.fm.cwd.hash + '" /><input type="hidden" name="filename" value="' + t.name + '" /></form>');
			var lb = $('<p style="margin:15px 0;font-size:12px"/>').html('链接有效期至：');
			var timeinput = $('<input name="validdate" type="text" >')
			var text = $('<p style="margin:15px 0;font-size:12px">生成外链地址：<input class="filelink" type="text" name="path" autocomplete="off"/></p>');
			var d = $('<div/>');
			var nowtime = new Date();
			nowtime.setHours(nowtime.getHours()+1);
			timeinput.val(nowtime.Format("yyyy-MM-dd hh:mm:ss"));
			timeinput.datetimepicker({
				showSecond: true, //显示秒
				timeFormat: 'hh:mm:ss',//格式化时间
				stepHour: 1,//设置步长
				stepMinute: 1,
				stepSecond: 10
			});
			d.append(f.append(text).append(lb.append(timeinput))).dialog({
		        dialogClass: 'el-finder-dialog',
		        title: t.name,
		        modal: true,
		        resizable: false,
		        close: function () {
		            self.fm.lockShortcuts();
		        },
		        buttons: {		            
		            "生成外链": function () {
		            	var o = {
		    					cmd       : 'link',
		    					current   : self.fm.cwd.hash,
		    					filename  : t.name,
		    					date      : timeinput.val(),
		    					masterdc  : t.masterdc,
		    					ownerid   : t.ownerid
		    				};
		            	self.fm.ajax(o, function(data) {
		            		if(typeof(data.error)=="undefined"){
		            			$('input.filelink',text).val(data.url);
		            		}else{
		            			self.error(data.error);
		            		}
		    			}, {force : true});
		            },
		            "复制":function(){}
		        }
		    });
			
			var dlgButton = $('.ui-dialog-buttonpane button',d.parent()).eq(1);			
			dlgButton.zclip({
        		path:'../res/ZeroClipboard.swf',
        		copy:function(){
        			return $('input.filelink',text).val();
    			},
    			afterCopy:function(){
    				alert('复制成功');
    				$('input.filelink',text).focus();
    	        }
    		});
		});

	}
	
	/**
	 * Move selection in current dir 
	 *
	 * @param Boolean  move forward?
	 * @param Boolean  clear current selection?
	 **/
	function moveSelection(forward, reset) {
		var p, _p, cur;
		
		if (!$('[key]', self.cwd).length) {
			return;
		}
		
		if (self.fm.selected.length == 0) {
			p = $('[key]:'+(forward ? 'first' : 'last'), self.cwd);
			self.fm.select(p);
		} else if (reset) {
			p  = $('.ui-selected:'+(forward ? 'last' : 'first'), self.cwd);
			_p = p[forward ? 'next' : 'prev']('[key]');
			if (_p.length) {
				p = _p;
			}
			self.fm.select(p, true);
		} else {
			if (self.pointer) {
				cur = $('[key="'+self.pointer+'"].ui-selected', self.cwd);
			}
			if (!cur || !cur.length) {
				cur = $('.ui-selected:'+(forward ? 'last' : 'first'), self.cwd);
			}
			p = cur[forward ? 'next' : 'prev']('[key]');

			if (!p.length) {
				p = cur;
			} else {
				if (!p.hasClass('ui-selected')) {
					self.fm.select(p);
				} else {
					if (!cur.hasClass('ui-selected')) {
						self.fm.unselect(p);
					} else {
						_p = cur[forward ? 'prev' : 'next']('[key]')
						if (!_p.length || !_p.hasClass('ui-selected')) {
							self.fm.unselect(cur);
						} else {
							while ((_p = forward ? p.next('[key]') : p.prev('[key]')) && p.hasClass('ui-selected')) {
								p = _p;
							}
							self.fm.select(p);
						}
					}
				}
			} 
		}
		self.pointer = p.attr('key');
		self.fm.checkSelectedPos(forward);
	}
	
}

})(jQuery);