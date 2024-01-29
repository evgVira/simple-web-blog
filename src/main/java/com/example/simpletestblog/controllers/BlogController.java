package com.example.simpletestblog.controllers;

import com.example.simpletestblog.models.Post;
import com.example.simpletestblog.repository.PostRepository;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final PostRepository postRepository;

    @Autowired
    BlogController(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    @GetMapping
    public String blog(Model model){
        model.addAttribute("posts", postRepository.findAll());
        return "blog";
    }

    @GetMapping("/add")
    public String blogAdd(){
        return "blogAdd";
    }

    @PostMapping("/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String fullText,
                              Model model){
        Post post = new Post(title, anons, fullText);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/{id}")
    public String blogDetails(@PathVariable(value = "id")Long id, Model model){
        if(!postRepository.existsById(id)) {
            return "redirect:/";
        }
        Optional<Post> post = postRepository.findById(id);
        model.addAttribute("post", post.get());
        return "blogDetails";
    }

    @GetMapping("/{id}/edit")
    public String blogEdit(@PathVariable(value = "id")Long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        model.addAttribute("post", post.get());
        return "blogEdit";
    }

    @PostMapping("/{id}/edit")
    public String blogPostEdit(@PathVariable(value = "id")Long id,
                               @RequestParam String title,
                               @RequestParam String anons,
                               @RequestParam String fullText,
                               Model model){
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFullText(fullText);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id")Long id){
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        postRepository.deleteById(id);
        return "redirect:/blog";
    }
}
