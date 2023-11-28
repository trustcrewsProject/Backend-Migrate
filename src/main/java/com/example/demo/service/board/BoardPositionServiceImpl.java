package com.example.demo.service.board;

import com.example.demo.model.board.Board;
import com.example.demo.model.board.BoardPosition;
import com.example.demo.model.position.Position;
import com.example.demo.repository.board.BoardPositionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardPositionServiceImpl implements BoardPositionService {

    private final BoardPositionRepository boardPositionRepository;

    public BoardPosition getBoardPositionEntity(Board board, Position position){
        return new BoardPosition(board, position);
    }

    @Override
    public BoardPosition save(BoardPosition boardPosition) {
        return boardPositionRepository.save(boardPosition);
    }

}
