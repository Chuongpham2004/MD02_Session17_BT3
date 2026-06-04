create table books
(
    id             serial primary key,
    title          varchar(255)   not null,
    author         varchar(255)   not null,
    published_year int            not null,
    price          decimal(10, 2) not null
);

create or replace procedure add_book(p_title varchar, p_author varchar, p_published int, p_price decimal)
    language plpgsql
as
$$
begin
    insert into books(title, author, published_year, price) VALUES (p_title, p_author, p_published, p_price);
end;
$$;

create or replace procedure update_book(p_id int, new_title varchar, new_author varchar, new_year int,
                                        new_price decimal)
    language plpgsql
as
$$
begin
    update books set title = new_title, author=new_author, published_year=new_year, price=new_price where id = p_id;
end;
$$;

create or replace procedure delete_book(p_id int)
    language plpgsql
as
$$
begin
    delete from books where id = p_id;
end;
$$;

create or replace function find_books_by_author(p_author varchar)
    returns table
            (
                id             int,
                title          varchar,
                author         varchar,
                published_year int,
                price          decimal
            )
    language plpgsql
as
$$
begin
    return query select b.id, b.title, b.author, b.published_year, b.price from books b where author = p_author;
end;
$$;

create or replace function list_all_books()
    returns table
            (
                id             int,
                title          varchar,
                author         varchar,
                published_year int,
                price          decimal
            )
    language plpgsql
as
$$
begin
    return query select b.id, b.title, b.author, b.published_year, b.price from books b;
end;
$$;
