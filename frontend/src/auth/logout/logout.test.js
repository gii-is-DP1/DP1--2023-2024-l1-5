import React from 'react'
import { render, fireEvent, screen, waitFor } from './../../test-utils'
import Logout from './index'
import tokenService from '../../services/token.service'

jest.mock('../../services/token.service', () => ({
  removeUser: jest.fn(),
  getUser: jest.fn(),
}));

const mockAlert = jest.spyOn(window, 'alert').mockImplementation(() => {});

describe('Pruebas de Logout', () => {
    test('Logout function shows alert if no user is logged in', async () => {
        // Remove token from local storage
        window.localStorage.removeItem('jwt');

        render(<Logout />);

        const yesButton = screen.getByRole('button', { name: /Yes/i });

        fireEvent.click(yesButton);

        await waitFor(() => {
        expect(tokenService.removeUser).not.toHaveBeenCalled();
        expect(window.location.href).not.toBe('/');
        });
    });
});
