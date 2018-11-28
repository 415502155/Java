package cn.edugate.esb.util;

import java.util.HashMap;
import java.util.Map;

public class ExtUtils {
	
	private final static Map<String, String> map = new HashMap<String, String>() {
		/**
		 * Content-Type值对照表 
		 */
		private static final long serialVersionUID = 1L;

	{ 
        put( "ai" , "application/postscript" ); 
        put( "aif" , "audio/x-aiff" ); 
        put( "aifc" , "audio/x-aiff" );        
        put("aiff","audio/x-aiff");
    	put("asc","text/plain"); 
    	put("au","audio/basic"); 
    	put("avi","video/x-msvideo"); 
    	put("bcpio","application/x-bcpio"); 
    	put("bin","application/octet-stream"); 
    	put("c","text/plain"); 
    	put("cc","text/plain"); 
    	put("ccad","application/clariscad"); 
    	put("cdf","application/x-netcdf"); 
    	put("class","application/octet-stream"); 
    	put("cpio","application/x-cpio"); 
    	put("cpt","application/mac-compactpro"); 
    	put("csh","application/x-csh"); 
    	put("css","text/css"); 
    	put("dcr","application/x-director"); 
    	put("dir","application/x-director"); 
    	put("dms","application/octet-stream"); 
    	put("doc","application/msword"); 
    	put("drw","application/drafting"); 
    	put("dvi","application/x-dvi"); 
    	put("dwg","application/acad"); 
    	put("dxf","application/dxf"); 
    	put("dxr","application/x-director"); 
    	put("eps","application/postscript"); 
    	put("etx","text/x-setext"); 
    	put("exe","application/octet-stream"); 
    	put("ez","application/andrew-inset"); 
    	put("f","text/plain"); 
    	put("f90","text/plain"); 
    	put("fli","video/x-fli"); 
    	put("gif","image/gif"); 
    	put("GIF","image/gif"); 
    	put("gtar","application/x-gtar"); 
    	put("gz","application/x-gzip"); 
    	put("h","text/plain"); 
    	put("hdf","application/x-hdf"); 
    	put("hh","text/plain"); 
    	put("hqx","application/mac-binhex40"); 
    	put("htm","text/html"); 
    	put("html","text/html"); 
    	put("ice","x-conference/x-cooltalk"); 
    	put("ief","image/ief"); 
    	put("iges","model/iges"); 
    	put("igs","model/iges"); 
    	put("ips","application/x-ipscript"); 
    	put("ipx","application/x-ipix"); 
    	put("jpe","image/jpeg"); 
    	put("jpeg","image/jpeg"); 
    	put("JPEG","image/jpeg");
    	put("jpg","image/jpeg"); 
    	put("JPG","image/jpeg"); 
    	put("js","application/x-JavaScript"); 
    	put("kar","audio/midi"); 
    	put("latex","application/x-latex"); 
    	put("lha","application/octet-stream"); 
    	put("lsp","application/x-lisp"); 
    	put("lzh","application/octet-stream"); 
    	put("m","text/plain"); 
    	put("man","application/x-troff-man"); 
    	put("me","application/x-troff-me"); 
    	put("mesh","model/mesh"); 
    	put("mid","audio/midi"); 
    	put("midi","audio/midi"); 
    	put("mif","application/vndput(\"mif"); 
    	put("mime","www/mime"); 
    	put("mov","video/quicktime"); 
    	put("movie","video/x-sgi-movie"); 
    	put("mp2","audio/mpeg"); 
    	put("mp3","audio/mpeg"); 
    	put("mpe","video/mpeg"); 
    	put("mpeg","video/mpeg"); 
    	put("mpg","video/mpeg"); 
    	put("mpga","audio/mpeg"); 
    	put("ms","application/x-troff-ms"); 
    	put("msh","model/mesh"); 
    	put("nc","application/x-netcdf"); 
    	put("oda","application/oda"); 
    	put("pbm","image/x-portable-bitmap"); 
    	put("pdb","chemical/x-pdb"); 
    	put("pdf","application/pdf"); 
    	put("pgm","image/x-portable-graymap"); 
    	put("pgn","application/x-chess-pgn"); 
    	put("png","image/png"); 
    	put("PNG","image/png"); 
    	put("pnm","image/x-portable-anymap"); 
    	put("pot","application/mspowerpoint"); 
    	put("ppm","image/x-portable-pixmap"); 
    	put("pps","application/mspowerpoint"); 
    	put("ppt","application/mspowerpoint"); 
    	put("ppz","application/mspowerpoint"); 
    	put("pre","application/x-freelance"); 
    	put("prt","application/pro_eng");        
}};
	
	public final static String getExt(String s) {
		return map.get(s);
	}
}
