create table todo_item
(
    id   int auto_increment
        primary key,
    done bit          null,
    text varchar(255) null
);