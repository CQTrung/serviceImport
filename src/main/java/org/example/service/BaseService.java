package org.example.service;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public abstract class BaseService {

    protected BaseService() {
    }

//    protected UserAuthentication getUser() {
//        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth == null) {
//            throw new NotFoundException(MessageTranslator.toLocale("authentication.invalid"));
//        }
//        return (UserAuthentication) auth.getPrincipal();
//    }
//
//    protected Integer getBranchId() {
//        UserAuthentication user = getUser();
//        return user.getBranchId();
//    }

    public Pageable createPageRequest(int pageNumber, int recordNumber) {
        return PageRequest.of(Math.max(pageNumber, 0), Math.max(recordNumber, 0));
    }

    public static Pageable createPageRequest(int pageNumber, int recordNumber, Sort sort) {
        return PageRequest.of(Math.max(pageNumber, 0), Math.max(recordNumber, 0), sort);
    }

    public Pageable createPageRequest(int pageNumber, int recordNumber, List<Sort.Order> orders) {
        return PageRequest.of(pageNumber, recordNumber, Sort.by(orders));
    }
}
