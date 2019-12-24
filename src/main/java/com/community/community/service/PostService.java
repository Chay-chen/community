package com.community.community.service;

import com.community.community.dto.PostDTO;
import com.community.community.mapper.PostMapper;
import com.community.community.mapper.UserMapper;
import com.community.community.model.Post;
import com.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    public List<PostDTO> list() {
        List<Post> posts = postMapper.list();
        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post : posts){
            User user = userMapper.findById(post.getCreator());
            PostDTO postDTO= new PostDTO();
            BeanUtils.copyProperties(post,postDTO);
            postDTO.setUser(user);
            postDTOList.add(postDTO);
        }

        return postDTOList;
    }
}
