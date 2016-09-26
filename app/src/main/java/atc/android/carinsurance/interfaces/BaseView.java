package atc.android.carinsurance.interfaces;

/**
 * Created by jorgecasariego on 26/9/16.
 *
 * Interfaz de comportamiento general de vistas
 */

public interface BaseView<T> {
    // Crea un vinculo view-presenter para relacionar ambas instancias
    void setPresenter(T presenter);
}