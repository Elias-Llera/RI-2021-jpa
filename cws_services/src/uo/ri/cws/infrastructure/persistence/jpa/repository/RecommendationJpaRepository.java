package uo.ri.cws.infrastructure.persistence.jpa.repository;

import uo.ri.cws.application.repository.RecommendationRepository;
import uo.ri.cws.domain.Recommendation;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;

public class RecommendationJpaRepository extends
		BaseJpaRepository<Recommendation> implements RecommendationRepository {

}
