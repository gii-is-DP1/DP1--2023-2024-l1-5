import React from 'react'
import { render, screen, testRenderList} from './../test-utils'
import GameRules from './GameRules'

describe('Pruebas de las reglas de juego', () => {
  test('renders correctly', () => {
    render(<GameRules />);

    // Verifica que el componente se renderice correctamente
    // Busca el primer elemento img en la primera fila
    const firstImageInFirstRow = screen.getByAltText('Imagen 1');

    // Asegúrate de que el elemento img exista en el DOM
    expect(firstImageInFirstRow).toBeInTheDocument();
  });

  test('renders all images', () => {
    render(<GameRules />);

    // Verifica que se rendericen todas las imágenes
    for (let i = 1; i <= 8; i++) {
      const image = screen.getByAltText(`Imagen ${i}`);
      expect(image).toBeInTheDocument();
    }
  });

  test('renders images with correct URLs', () => {
    render(<GameRules />);

    const expectedURLs = [
      'https://i.imgur.com/P5d3qbK.jpeg', // 1
      'https://i.imgur.com/poq7e8a.jpeg', // 3
      'https://i.imgur.com/SPyjIzq.jpeg', // 5
      'https://i.imgur.com/dnbylbJ.jpeg', // 7
      'https://i.imgur.com/xg0Ip2C.jpeg', // 2
      'https://i.imgur.com/doqI44d.jpeg', // 4
      'https://i.imgur.com/FM0lzzV.jpeg', // 6
      'https://i.imgur.com/ENbj5D3.jpeg', // 8
    ];

    // Verifica que las imágenes tengan las URLs correctas
    expectedURLs.forEach((url, index) => {
      const image = screen.getByAltText(`Imagen ${index + 1}`);
      expect(image.src).toBe(url);
    });
  });
});

