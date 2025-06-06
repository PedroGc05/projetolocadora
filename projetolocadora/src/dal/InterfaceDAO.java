package dal;

import java.util.List;

public interface InterfaceDAO<T> {
    void salvar(T entidade);

    void atualizar(T entidade);

    void deletar(int id);

    T buscarPorId(int id);

    List<T> buscarTodos();
}