package com.myBlog.MyBlog1.controller;


import com.myBlog.MyBlog1.payload.PostDto;
import com.myBlog.MyBlog1.payload.PostResponse;
import com.myBlog.MyBlog1.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
 @Autowired
    private PostService postService;
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){
       if(result.hasErrors()){
           return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
       }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
@DeleteMapping("{id}")
    public ResponseEntity<String> deletePost(@PathVariable long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("Post is Deleted",HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id){
        PostDto postDto =postService.getPostById(id);
        return new ResponseEntity<>(postDto,HttpStatus.OK);

    }
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable long id,@RequestBody PostDto postDto){
        PostDto dto=postService.updatePost(id,postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //http://localhost:8080/api/posts?pageNo=1&pageSize=3&sortBy=description&sortDir=asc
    @GetMapping
    public PostResponse getAllPosts(
        @RequestParam(name="pageNo",required = false,defaultValue = "0")int pageNo,
        @RequestParam(name="pageSize",required = false,defaultValue = "5")int pageSize,
        @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        PostResponse response = postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
        return response;
    }


}