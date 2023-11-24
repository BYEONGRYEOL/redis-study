package com.example.hanghaero.service;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hanghaero.dto.column.ColRequestDto;
import com.example.hanghaero.dto.column.ColResponseDto;
import com.example.hanghaero.entity.Board;
import com.example.hanghaero.entity.Column;
import com.example.hanghaero.repository.BoardRepository;
import com.example.hanghaero.repository.ColumnRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "ColumnService")
public class ColumnService {
	private final BoardRepository boardRepository;
	private final ColumnRepository columnRepository;
	private final RedissonClient redissonClient;

	public ColResponseDto createColumn(Long boardId, ColRequestDto requestDto) {
		Board findBoard = findBoard(boardId);
		int position = 0;
		if (columnRepository.getLastPositon(boardId) != null) {
			position = columnRepository.getLastPositon(boardId);
		}
		return new ColResponseDto(columnRepository.save(
			new Column(findBoard, requestDto, position))
		);
	}

	@Transactional
	public ColResponseDto updateColumn(Long boardId, Long columnId, ColRequestDto requestDto) {
		findBoard(boardId);
		Column findColumnObject = findColumn(columnId);
		try {
			findColumnObject.update(requestDto);
		} catch (Exception e) {
			e.getMessage();
		}
		return new ColResponseDto(findColumnObject);
	}

	@Transactional
	public ResponseEntity deleteColumn(Long boardId, Long columnId) {
		findBoard(boardId);
		Column column = findColumn(columnId);
		try {
			columnRepository.delete(column);
		} catch (Exception e) {
			e.getMessage();
		}
		return ResponseEntity.ok().body("칼럼이 삭제되었습니다.");
	}

	@Transactional
	public Object moveColumn(Long boardId, Long columnId, int newPosition) {
		Column findColumn = findColumn(columnId); //칼럼조회
		log.info("현재 위치 " + findColumn.getPosition());
		log.info("변경할 위치" + newPosition);
		Column targetColumn = columnRepository.findByPosition(newPosition);
		if (targetColumn != null) {
			try {
				targetColumn.updatePosition(findColumn.getPosition());
			} catch (Exception e) {
				log.error("e.getMessage() :" + e.getMessage());
			}
		}
		try {
			findColumn.updatePosition(newPosition);
		} catch (Exception e) {
			log.error("e.getMessage() :" + e.getMessage());
		}

		return ResponseEntity.ok().body(columnId + "번 칼럼 이동");
	}

	@Transactional
	public Object moveColumnWithRedis(Long boardId, Long columnId, int newPosition) {
		Column findColumn = findColumn(columnId); //칼럼조회
		RLock lock = redissonClient.getLock(String.format("moveColumn:id:%d", columnId));
		try {
			boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
			if (!available) {
				log.info("redisson getLock timeout");
			}
			log.info("현재 위치 " + findColumn.getPosition());
			log.info("변경할 위치" + newPosition);
			Column targetColumn = columnRepository.findByPosition(newPosition);
			if (targetColumn != null) {
				targetColumn.updatePosition(findColumn.getPosition());
			}
			findColumn.updatePosition(newPosition);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			lock.unlock();
		}

		return ResponseEntity.ok().body(columnId + "번 칼럼 이동");
	}

	private Board findBoard(Long boardId) {
		Board board = boardRepository.findById(boardId).orElseThrow(
			() -> new IllegalArgumentException("조회되는 보드가 없습니다.")
		);
		return board;
	}

	private Column findColumn(Long columnId) {
		Column column = columnRepository.findById(columnId).orElseThrow(
			() -> new EntityNotFoundException("칼럼을 찾을 수 없습니다.")
		);
		return column;
	}

}
