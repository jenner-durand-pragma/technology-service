CREATE TABLE IF NOT EXISTS technology (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(1023)
);

CREATE TABLE IF NOT EXISTS capacity_technology (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    technology_id BIGINT NOT NULL,
    capacity_id BIGINT NOT NULL,

    CONSTRAINT fk_capacity_technology_technology
        FOREIGN KEY (technology_id)
        REFERENCES technology(id)
)
