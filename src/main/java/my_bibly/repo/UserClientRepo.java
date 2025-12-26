package my_bibly.repo;

import my_bibly.domain.users.UserClient;
import my_bibly.repo.abstr.MyAbstractUserRepo;

public interface UserClientRepo extends MyAbstractUserRepo<UserClient, Long> {
}
