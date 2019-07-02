package zarzyka.jagoda.shelter.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import zarzyka.jagoda.shelter.user.model.UserEntity;

import java.util.List;

@RequiredArgsConstructor

public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<UserEntity> findByShelter(String shelterUuid) {
        LookupOperation lookup = LookupOperation.newLookup().from("user").localField("_id").foreignField("shelterUuid")
                .as("users");

        AggregationResults<UserEntity> result = mongoTemplate.aggregate(
                Aggregation.newAggregation(lookup, Aggregation.match(Criteria.where("shelterUuid").is(shelterUuid))), "user",
                UserEntity.class);

        return result.getMappedResults();
    }
}
