``sql
CREATE TABLE IF NOT EXISTS boards (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      name VARCHAR(120) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS board_columns (
                                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                             board_id BIGINT NOT NULL,
                                             name VARCHAR(120) NOT NULL,
    position INT NOT NULL,
    type ENUM('INITIAL','PENDING','FINAL','CANCEL') NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_board_columns_board FOREIGN KEY (board_id) REFERENCES boards(id) ON DELETE CASCADE,
    UNIQUE KEY uq_board_position (board_id, position)
    );

CREATE TABLE IF NOT EXISTS cards (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     board_id BIGINT NOT NULL,
                                     column_id BIGINT NOT NULL,
                                     title VARCHAR(160) NOT NULL,
    description TEXT,
    blocked BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cards_board FOREIGN KEY (board_id) REFERENCES boards(id) ON DELETE CASCADE,
    CONSTRAINT fk_cards_column FOREIGN KEY (column_id) REFERENCES board_columns(id) ON DELETE RESTRICT
    );