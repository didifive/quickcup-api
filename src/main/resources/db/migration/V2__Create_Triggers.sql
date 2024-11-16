CREATE OR REPLACE FUNCTION prevent_delete_or_update_item_pedido() RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'DELETE' THEN
        RAISE EXCEPTION 'Não é permitido excluir registros da tabela Item_Pedido';
    ELSIF TG_OP = 'UPDATE' THEN
        RAISE EXCEPTION 'Não é permitido alterar registros da tabela Item_Pedido';
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER prevent_delete_or_update_item_pedido_trigger
BEFORE DELETE OR UPDATE ON Item_Pedido
FOR EACH ROW
EXECUTE FUNCTION prevent_delete_or_update_item_pedido();



CREATE OR REPLACE FUNCTION prevent_delete_pedido() RETURNS TRIGGER AS $$
BEGIN
    RAISE EXCEPTION 'Não é permitido excluir registros da tabela Pedido';
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER prevent_delete_pedido_trigger
BEFORE DELETE ON Pedido
FOR EACH ROW
EXECUTE FUNCTION prevent_delete_pedido();


CREATE OR REPLACE FUNCTION allow_update_status_only() RETURNS TRIGGER AS $$
BEGIN
    IF NEW.status IS DISTINCT FROM OLD.status THEN
        RETURN NEW;
    ELSE
        RAISE EXCEPTION 'Apenas a coluna status pode ser alterada na tabela Pedido';
    END IF;
    RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER allow_update_status_only_trigger
BEFORE UPDATE ON Pedido
FOR EACH ROW
EXECUTE FUNCTION allow_update_status_only();

