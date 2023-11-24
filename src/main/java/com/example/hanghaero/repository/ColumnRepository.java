package com.example.hanghaero.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.hanghaero.entity.Column;

@Repository
public interface ColumnRepository extends JpaRepository<Column, Long> {
	@Query(value = "select * from columns where position=? ", nativeQuery = true)
	Column findByPosition(int position);

	@Query(value = "select position from columns where board_id=? order By position DESC limit 1", nativeQuery = true)
	Integer getLastPositon(Long boardId);
}
