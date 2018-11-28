package cn.edugate.esb.redis.cache;

import cn.edugate.esb.redis.cache.MenuCacheProvider;
import cn.edugate.esb.redis.cache.OrgUserCacheProvider;
import cn.edugate.esb.redis.cache.OrganizationCacheProvider;
import cn.edugate.esb.redis.cache.RoleCacheProvider;
import cn.edugate.esb.redis.cache.TeacherRoleCacheProvider;
import cn.edugate.esb.redis.cache.UcUserCacheProvider;
import cn.edugate.esb.redis.cache.UcuserOrguserCacheProvider;
import cn.edugate.esb.redis.cache.UserAccountCacheProvider;

/**
 * 缓存定义
 * 
 * @Name: 
 * @Description:
 */
public class Caches {
	public final static OrgUserCacheProvider ORGUSERS = new OrgUserCacheProvider();
	public final static UcUserCacheProvider UCUSERS = new UcUserCacheProvider();
	public final static UcuserOrguserCacheProvider UCUSER2ORGUSER = new UcuserOrguserCacheProvider();
	public final static UserAccountCacheProvider USERACCOUNT = new UserAccountCacheProvider();
	public final static TeacherRoleCacheProvider TEACHERROLE = new TeacherRoleCacheProvider();
	public final static RoleCacheProvider ROLE = new RoleCacheProvider();
	public final static OrganizationCacheProvider ORGANIZATION = new OrganizationCacheProvider();
	public final static MenuCacheProvider MENU = new MenuCacheProvider();
	public final static RightCacheProvider RIGHT = new RightCacheProvider();
	public final static GroupCacheProvider GROUP = new GroupCacheProvider();
	public final static GroupMemberCacheProvider GROUPMEMBER = new GroupMemberCacheProvider();
	public final static GradeCacheProvider GRADE = new GradeCacheProvider();
	public final static ClassesCacheProvider CLASSES = new ClassesCacheProvider();
	public final static DepartmentCacheProvider DEPARTMEMBER = new DepartmentCacheProvider();
	public final static TeacherCacheProvider TEACHERMEMBER = new TeacherCacheProvider();
	public final static CourseCacheProvider COURSEMEMBER = new CourseCacheProvider();
	public final static TechRangeCacheProvider TECHRANGE = new TechRangeCacheProvider();
	public final static RoleLabelCacheProvider ROLELABEL = new RoleLabelCacheProvider();
	public final static StudentCacheProvider STUDENT = new StudentCacheProvider();
	public final static CampusCacheProvider CAMPUS = new CampusCacheProvider();
	public final static FieldCacheProvider FIELD = new FieldCacheProvider();
	public final static OrgTreeCacheProvider ORGTREE = new OrgTreeCacheProvider();
	public final static ParentCacheProvider PARENT = new ParentCacheProvider();
	public final static ClassStudentCacheProvider CLASSSTUDENT = new ClassStudentCacheProvider();
	public final static StudentParentCacheProvider STUDENTPARENT = new StudentParentCacheProvider();
	public final static WxConfigCacheProvider WXCONFIG = new WxConfigCacheProvider();
	public final static AppCacheProvider APP = new AppCacheProvider();
	public final static PictureCacheProvider PICTURE = new PictureCacheProvider();
	public final static CardCacheProvider CARD = new CardCacheProvider();
	public final static Grade2ClasCacheProvider GRADECLASS = new Grade2ClasCacheProvider();
	public final static IconCacheProvider ICON = new IconCacheProvider();
}
