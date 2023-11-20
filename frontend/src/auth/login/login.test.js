import React from 'react'
import { render, fireEvent, screen, waitFor} from "./../../test-utils"
import Login from "./index"

describe("Pruebas del login", () => {
    test("Iniciar sesión con credenciales de player", async () => {

        const fetchSpy = jest.spyOn(global, 'fetch').mockResolvedValue({
            status: 200,
            json: () => Promise.resolve({ token: 'someToken' }),
        });
        
        render(<Login/>);
        const usuarioInput = screen.getByRole('textbox', { name: /username/i });
        const contraseñaInput = screen.getByLabelText(/password/i);
        const submitButton = screen.getByRole('button', { name: /Login/i, className: 'auth-button' });

        fireEvent.change(usuarioInput, { target: { value: 'player1' } });
        fireEvent.change(contraseñaInput, { target: { value: 'v3t' } });

        fireEvent.click(submitButton);

        // Esperar a que se complete la solicitud de inicio de sesión (puede ser una API real o una simulación)
        await waitFor(() => expect(fetchSpy).toHaveBeenCalledTimes(1));

        // Restaurar fetch a su implementación original
        fetchSpy.mockRestore();
        });
}
);