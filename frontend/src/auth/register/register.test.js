import React from 'react';
import { render, fireEvent, screen, waitFor } from "./../../test-utils";
import Register from "./index";

describe("Pruebas del registro de jugador", () => {
  test("Registrar jugador exitosamente", async () => {

    const fetchSpy = jest.spyOn(global, 'fetch').mockResolvedValue({
      status: 200,
      json: () => Promise.resolve({ token: 'someToken' }),
    });

    render(<Register />);
    screen.debug();
    
    const playerButton = screen.getByRole('button', { name: /Player/i, className: 'auth-button'}); 
    fireEvent.click(playerButton);

    // Seleccionar los elementos del formulario
    const usernameInput = screen.getByLabelText(/ Username/i);
    const passwordInput = screen.getByLabelText(/Password/i);
    const firstNameInput = screen.getByLabelText(/First Name/i);
    const lastNameInput = screen.getByLabelText(/Last Name/i);
    const playerUsernameInput = screen.getByLabelText(/Player Username/i);
    const imageInput = screen.getByLabelText(/Image/i);
    const submitButton = screen.getByRole('button', { name: /Save/i, className: 'auth-button' });

    // Simular la entrada de datos
    fireEvent.change(usernameInput, { target: { value: 'testPlayer' } });
    fireEvent.change(passwordInput, { target: { value: 'testPassword' } });
    fireEvent.change(firstNameInput, { target: { value: 'John' } });
    fireEvent.change(lastNameInput, { target: { value: 'Doe' } });
    fireEvent.change(playerUsernameInput, { target: { value: 'player123' } });
    fireEvent.change(imageInput, { target: { value: 'path/to/image.jpg' } });

    // Simular el envío del formulario
    fireEvent.click(submitButton);

    // Esperar a que se complete la solicitud de registro
    //await waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1));

    // Restaurar fetch a su implementación original
    fetchSpy.mockRestore();
  });
});
