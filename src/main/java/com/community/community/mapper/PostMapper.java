package com.community.community.mapper;

import com.community.community.dto.PostDTO;
import com.community.community.model.Post;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostMapper {
    @Insert("insert into post (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Post post);

    @Select("select * from post limit #{offset},#{size}")
    List<Post> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from post")
    Integer count();

    @Select("select * from post where creator = #{userId} limit #{offset},#{size}")
    List<Post> listByUserId(@Param(value = "userId") Integer userId, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from post where creator = #{userId}")
    Integer countByUserID(@Param(value = "userId") Integer userId);

    @Select("select * from post where id = #{id}")
    Post getById(@Param(value = "id") Integer id);
}
