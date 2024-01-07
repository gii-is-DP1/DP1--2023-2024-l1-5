import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import GameHistory from './gameHistory';

jest.mock('../services/token.service', () => ({
  getLocalAccessToken: jest.fn(() => 'mockedAccessToken'),
  getUser: jest.fn(() => ({ id: 1 })),
}));

describe('GameHistory', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('renders games history table with no games', async () => {

    global.fetch = jest.fn().mockResolvedValue({
      json: jest.fn().mockResolvedValue([]),
    });

    render(<GameHistory />);

    await waitFor(() => expect(screen.getByText("You havent play any game yet.")).toBeInTheDocument());
  });


  test('renders games history table with games and dropdown', async () => {
    // Mock fetch
    global.fetch = jest.fn().mockResolvedValue({
      json: jest.fn().mockResolvedValue([
        {
          id: 1,
          gameMode: 'IN_PROGRESS',
          creator: 1,
          playerList: [
            {
              id: 1,
              user: {
                id: 1,
                username: 'player1',
                email: ''
              }
            },
            {
              id: 2,
              user: {
                id: 2,
                username: 'player2',
                email: ''
              }
            }
          ],
        },
      ]),
    });

    render(<GameHistory />);
    await waitFor(() => expect(screen.getByText('IN_PROGRESS')).toBeInTheDocument() 
    && expect(screen.getByText('player1')).toBeInTheDocument()
    && expect(screen.getByText('player2')).toBeInTheDocument());

    screen.debug();
  });
  

});
