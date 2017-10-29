package org.example.data;

import org.example.domain.QQuotation;
import org.example.domain.Quotation;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface QuotationRepository extends ModelRepository<Quotation>, QueryDslPredicateExecutor<Quotation>
{
  default Iterable<Quotation> findAllByRepresentativeName(final String name)
  {
    return findAll(QQuotation.quotation
                       .representative
                       .jobs
                       .any()
                       .employee
                       .person
                       .name
                       .containsIgnoreCase(name));
  }
}
