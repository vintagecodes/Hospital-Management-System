package com.appoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import org.springframework.data.mongodb.core.query.Update;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import org.springframework.stereotype.Service;

import com.appoint.model.DatabaseSequence;




@Service
public class SequenceGeneratorService {
	

	private MongoOperations mongoOperations;

    @Autowired
    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public int generateSequence(String seqName) {

        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true),
                DatabaseSequence.class);
        if(counter == null) {
        	counter = new DatabaseSequence();
        	counter.setId(seqName);
        	counter.setSeq(2000);
        	mongoOperations.insert(counter);
        }
        return (int) counter.getSeq();

    }
}
