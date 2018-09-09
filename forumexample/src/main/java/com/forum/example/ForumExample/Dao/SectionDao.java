package com.forum.example.ForumExample.Dao;

import com.forum.example.ForumExample.Model.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionDao extends JpaRepository<Section,Integer> {
    Section findByName(String name);
    Section findById(int id);
}
