package org.example.data;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.example.domain.QQuotation;
import org.example.domain.Quotation;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface QuotationRepository extends ModelRepository<Quotation>, QueryDslPredicateExecutor<Quotation>
{
  default Iterable<Quotation> findAllByRepresentativeName(final String name)
  {
    final QQuotation quotation = QQuotation.quotation;

    BooleanExpression query = quotation.customer.isNotNull();

    query = query.and(quotation.supplier.isNotNull());

    query = query.and(getPersonNameRestriction(quotation.representative.jobs.any().employee.person, name));

    return findAll(query);
  }
}
