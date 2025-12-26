package my_bibly.repo.abstr;

import my_bibly.domain.base.UserBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyAbstractUserRepo<A extends UserBase, B> extends JpaRepository<A, B> {

    UserBase findByPhone(B phone);

}
