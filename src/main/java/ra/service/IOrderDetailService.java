package ra.service;

import ra.model.domain.Users;

import java.util.List;
import java.util.Optional;

public interface IOrderDetailService<T> {

    Optional<T> findById(Users users,Long id);



}
