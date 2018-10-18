package tk.mybatis.springboot.dao.vani;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tk.mybatis.springboot.model.t_tmn_info;

@Mapper
public interface TmnInfoDao {
	//String sql = "select * from ( select a.*, rownum rn from ("+s+") a where rownum <= "+end+" ) where rn >= " +start;
//	@Select("select * from ( select a.*, rownum rn from t_tmn_info a where rownum <= #{end} ) where rn >= #{start}")
//	List<t_tmn_info>  getAll(@Param("end")int end ,@Param("start")int start);
//	
//	@Select("select * from t_tmn_info where terminal_id = #{terminal_id}")
//	t_tmn_info  findTmnInfoByTerminal(@Param("terminal_id")int terminal_id);
//	
//	@Select("select count(1) from t_tmn_info ")
//	int  findTmnInfoNum();
//	
//	List<t_tmn_info> queryAll(int cityId);
	
}
