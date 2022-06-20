package com.sandeep.medicalresearch.common.specifications;

import com.sandeep.medicalresearch.entity.ResearchDomain;
import com.sandeep.medicalresearch.entity.ResearchProjectEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ResearchProjectSpecification {

  public static Specification<ResearchProjectEntity> hasId(Long id) {
    return ((root, query, criteriaBuilder) -> {
      if(id == null)
      {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get("id"), id);
    });
  }

  public static Specification<ResearchProjectEntity> hasName(String name) {
    return ((root, query, criteriaBuilder) -> {
      if(name == null)
        return criteriaBuilder.conjunction();
      return criteriaBuilder.equal(root.get("name"), name);
    });
  }

  public static Specification<ResearchProjectEntity> hasDomain(ResearchDomain domain) {
    return (((root, query, criteriaBuilder) -> {
      if (domain == null)
        return criteriaBuilder.conjunction();
      return criteriaBuilder.equal(root.get("domain"), domain);
    }));
  }

  public static Specification<ResearchProjectEntity> hasFundingAmount(Integer funding_amount) {
    return ((root, query, criteriaBuilder) ->
    {
      if (funding_amount==null)
        return criteriaBuilder.conjunction();
      return criteriaBuilder.equal(root.get("funding_amount"), funding_amount);
    });
  }

  public static Specification<ResearchProjectEntity> fundingAmountLessThan(Integer amount) {
    return ((root, query, criteriaBuilder) ->
    {
      if (amount == null)
        return criteriaBuilder.conjunction();
      return criteriaBuilder.lessThan(root.get("funding_amount"), amount);
    });
  }

  public static Specification<ResearchProjectEntity> fundingAmountGreaterThan(Integer amount) {
    return ((root, query, criteriaBuilder) ->

    {
      if (null == amount)
        return criteriaBuilder.conjunction();
      return criteriaBuilder.greaterThan(root.get("funding_amount"), amount);
    });
  }

  public static Specification<ResearchProjectEntity> hasTeamCount(Integer team_count) {
    return ((root, query, criteriaBuilder) ->
    {
      if (null == team_count)
        return criteriaBuilder.conjunction();
      return criteriaBuilder.equal(root.get("team_count"), team_count);
    });
  }

  public static Specification<ResearchProjectEntity> teamCountLessThan(Integer team_count) {
    return ((root, query, criteriaBuilder) ->
    {
      if (team_count == null){
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.lessThan(root.get("team_count"), team_count);
    });
  }

  public static Specification<ResearchProjectEntity> teamCountGreaterThan(Integer team_count) {
    return ((root, query, criteriaBuilder) ->
    {
      if (null == team_count)
        return criteriaBuilder.conjunction();
      return criteriaBuilder.greaterThan(root.get("team_count"), team_count);
    });
  }
}
