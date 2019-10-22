package my.miaosha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import my.miaosha.domain.User;

@Mapper
public interface MiaoShaUserDao {
	
	@Select("select * from miaosha_user where id=#{id}")
	public User getById(@Param("id") long id);

	@Update("updata miaosha_user set password = #{password} where id = #{id}")
	public void updataPassword(User user);
}
