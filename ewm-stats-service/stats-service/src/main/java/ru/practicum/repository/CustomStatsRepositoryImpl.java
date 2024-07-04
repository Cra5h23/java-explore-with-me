package ru.practicum.repository;

import ru.practicum.model.Hit;
import ru.practicum.repository.projection.CountHitProjection;
import ru.practicum.service.StatsService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.time.ZoneId;
import java.util.List;

/**
 * @author Nikolay Radzivon
 * @Date 04.07.2024
 */
public class CustomStatsRepositoryImpl implements CustomStatsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CountHitProjection> getCountHit(StatsService.Params params) {
        var uris = params.getUris();
        var start = params.getStart();
        var end = params.getEnd();
        boolean unique = params.isUnique();

        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(CountHitProjection.class);
        var hitRoot = cq.from(Hit.class);

        Expression<Long> count = unique ? cb.countDistinct(hitRoot.get("ip")) : cb.count(hitRoot.get("ip"));
        Path<String> appPath = hitRoot.get("app");
        Path<String> uriPath = hitRoot.get("uri");

        var timePredicate = cb.between(hitRoot.get("timestamp"), start.atZone(ZoneId.systemDefault()),
                end.atZone(ZoneId.systemDefault()));

        Predicate uriPredicate;
        if (uris == null || uris.isEmpty()) {
            uriPredicate = cb.conjunction();
        } else if (uris.size() == 1) {
            String s = uris.get(0);
            uriPredicate = cb.like(cb.upper(hitRoot.get("uri")), s.toUpperCase() + "%");
        } else {
            uriPredicate = hitRoot.get("uri").in(uris);
        }

        cq.select(cb.construct(CountHitProjection.class, count, appPath, uriPath))
                .where(uriPredicate, timePredicate)
                .groupBy(appPath, uriPath)
                .orderBy(cb.desc(count));

        return entityManager.createQuery(cq).getResultList();
    }
}
