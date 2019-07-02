package zarzyka.jagoda.shelter.shelter.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import zarzyka.jagoda.shelter.shelter.model.ShelterEntity;
import zarzyka.jagoda.shelter.user.model.UserEntity;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomShelterRepositoryImpl implements CustomShelterRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Optional<ShelterEntity> findByUser(UserEntity userEntity) {
        LookupOperation lookup = LookupOperation.newLookup().from("user").localField("users._id").foreignField("_id")
                .as("users");

        AggregationResults<ShelterEntity> result = mongoTemplate.aggregate(
                Aggregation.newAggregation(lookup, Aggregation.match(Criteria.where("users.uuid").is(userEntity.getUuid()))), "shelter",
                ShelterEntity.class);

        List<ShelterEntity> mappedResults = result.getMappedResults();

        if (mappedResults.size() > 1) {
            throw new RuntimeException("Query returns not unique result");
        } else if (mappedResults.size() == 1) {
            return Optional.of(mappedResults.get(0));
        } else {
            return Optional.empty();
        }
    }
}
