import React from 'react'
import { render, screen} from '../test-utils'
import itGameView from './itGameView'

describe('Pruebas de la visualizacion de GameView', () => {
  test('cards´ visualization correctly', () => {
    render(< itGameView/>);

    const firstImageInFirstRow = screen.getByAltText('circle');

    expect(firstImageInFirstRow).toBeInTheDocument();
  });

});
