function printPlot(mu, sigma, color, k)  
    x = -10:0.1:10;
    y1 = gauss(x, mu(1), sigma(1));
    y2 = gauss(x, mu(2), sigma(2));
    plot(x, y1, color);
    plot(x, y2, color);
    for i = 1 : k
        fprintf('Mean%d %d\n', i, mu(i));
        fprintf('Sigma%d %d\n', i, sigma(i));
    end 
end

function val = gauss(x, mu, sigma)  
    val = (1 / (sigma * sqrt(2 * pi))) * exp(-(x - mu).^2 ./ (2 * sigma^2));
end