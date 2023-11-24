package com.example.hanghaero;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import com.example.hanghaero.controller.ColumnController;
import com.example.hanghaero.entity.Board;
import com.example.hanghaero.entity.Column;
import com.example.hanghaero.repository.BoardRepository;
import com.example.hanghaero.repository.ColumnRepository;

@SpringBootTest
public class ConcurrencyTest {
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private ColumnRepository columnRepository;
	@Autowired
	private ColumnController columnController;

	@BeforeEach
	public void beforeEach() {

		Board saveBoard = boardRepository.save(Board.builder().id(1L).name("board1").build());
		columnRepository.save(Column.builder().id(1L).board(saveBoard).position(1).build());
		columnRepository.save(Column.builder().id(2L).board(saveBoard).position(2).build());
	}

	@Test
	@DisplayName("위치가 1인 컬럼의 위치를 2~10까지의 위치로 변경")
	void moveColumn() throws InterruptedException {
		//given
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		CountDownLatch countDownLatch = new CountDownLatch(5);
		int[] moveTo = IntStream.rangeClosed(1, 10).toArray();

		//when
		for (int i = 0; i < 10; i++) {
			int position = i;
			executorService.submit(() -> {
				try {
					columnController.moveColumnWithoutRedis(1L, 1L, moveTo[position]);
				} finally {
					countDownLatch.countDown();
				}
			});
		}
		countDownLatch.await();

		//then
		Column actualColumn = columnRepository.findById(1L).orElseThrow();
		assertThat(actualColumn.getPosition()).isNotEqualTo(10);
	}

	@Test
	@DisplayName("위치가 1인 컬럼의 위치를 2~10까지의 위치로 변경")
	@Profile("redis")
	void moveColumnWithRedis() throws InterruptedException {
		//given
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		CountDownLatch countDownLatch = new CountDownLatch(5);
		int[] moveTo = IntStream.rangeClosed(1, 10).toArray();

		//when
		for (int i = 0; i < 10; i++) {
			int position = i;
			executorService.submit(() -> {
				try {
					columnController.moveColumnWithoutRedis(1L, 1L, moveTo[position]);
				} finally {
					countDownLatch.countDown();
				}
			});
		}
		countDownLatch.await();

		//then
		Column actualColumn = columnRepository.findById(1L).orElseThrow();
		assertThat(actualColumn.getPosition()).isNotEqualTo(10);
	}
}
