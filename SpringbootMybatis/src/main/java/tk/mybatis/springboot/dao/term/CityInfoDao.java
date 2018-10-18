package tk.mybatis.springboot.dao.term;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tk.mybatis.springboot.model.CityInfo;
import tk.mybatis.springboot.model.t_tmn_info;

@Mapper
public interface CityInfoDao {
	@Select("select * from ( select a.*, rownum rn from t_city_info a where rownum <= #{end} ) where rn >= #{start}")
	List<CityInfo>  getAll(@Param("end")int end ,@Param("start")int start);
	
}
