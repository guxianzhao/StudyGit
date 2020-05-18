package com.example.nosqldemo.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentReps {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Student> findAll() {
        return mongoTemplate.findAll(Student.class);
    }

    public Student findById(int id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Student.class);
    }

    public List<Student> findBySetId(int setId) {
        Query query = Query.query(Criteria.where("courseList._id").is(setId).andOperator(Criteria.where("_id").is(3)));
        return mongoTemplate.find(query, Student.class);
    }

    public void saveStudent(Student model, List<Course> courseList) {
        mongoTemplate.save(model);
        Query query = Query.query(Criteria.where("_id").is(model.getId()));
        Update update = new Update();
        for (Course course : courseList) {
            update.addToSet("courseList", course);
            mongoTemplate.upsert(query, update, Student.class);
        }
    }

    public void updateCourseName(int studentId, Course course) {
        Query query = Query.query(Criteria.where("_id").is(studentId)
                .and("courseList._id").is(course.getId()));
        Update update = new Update();
        update.set("courseList.$.name", course.getName());
        mongoTemplate.updateFirst(query, update, Student.class);
    }
}
