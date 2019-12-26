package com.community.community.service;

import com.community.community.dto.PaginationDTO;
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

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;
        Integer totalCount = postMapper.count();

        if (totalCount % size ==0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }


        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);
        Integer offset = size * (page - 1);
        if (offset < 0){
            offset = 1;
        }
        List<Post> posts = postMapper.list(offset,size);
        List<PostDTO> postDTOList = new ArrayList<>();

        for (Post post : posts){
            User user = userMapper.findById(post.getCreator());
            PostDTO postDTO= new PostDTO();
            BeanUtils.copyProperties(post,postDTO);
            postDTO.setUser(user);
            postDTOList.add(postDTO);
        }
        paginationDTO.setPosts(postDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = postMapper.countByUserID(userId);

        if (totalCount % size ==0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }


        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);
        if (offset < 0){
            offset = 1;
        }
        List<Post> posts = postMapper.listByUserId(userId,offset,size);
        List<PostDTO> postDTOList = new ArrayList<>();

        for (Post post : posts){
            User user = userMapper.findById(post.getCreator());
            PostDTO postDTO= new PostDTO();
            BeanUtils.copyProperties(post,postDTO);
            postDTO.setUser(user);
            postDTOList.add(postDTO);
        }
        paginationDTO.setPosts(postDTOList);
        return paginationDTO;
    }

    public PostDTO getById(Integer id) {
        Post post = postMapper.getById(id);
        PostDTO postDTO = new PostDTO();
        BeanUtils.copyProperties(post,postDTO);
        User user = userMapper.findById(post.getCreator());
        postDTO.setUser(user);
        return postDTO;
    }
}
