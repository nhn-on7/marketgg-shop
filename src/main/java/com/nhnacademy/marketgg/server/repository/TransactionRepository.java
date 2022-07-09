package com.nhnacademy.marketgg.server.repository;

import com.nhnacademy.marketgg.server.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Transaction.TransactionPk> {

}
