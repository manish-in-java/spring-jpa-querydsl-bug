package org.example.data;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.example.domain.QPerson;
import org.example.domain.QQuotation;
import org.example.domain.Quotation;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

public interface QuotationRepository extends ModelRepository<Quotation>, QueryDslPredicateExecutor<Quotation>
{
  default Iterable<Quotation> findAllByRepresentativeName(final String name)
  {
    final QQuotation quotation = QQuotation.quotation;
    final QPerson person = quotation.representative.jobs.any().employee.person;

    BooleanExpression query = quotation.customer.isNotNull();

    BooleanExpression subquery = person.firstName.concat(" ")
                                                 .concat(person.middleName.concat(" ").coalesce(""))
                                                 .concat(person.lastName)
                                                 .containsIgnoreCase(name);

    query = query.and(quotation.supplier.isNotNull());

    return findAll(query.and(subquery));
  }
}
