clear ; close all; clc

%% Init
data = load('sample-data.txt');
k = 2;
m = length(data);
section = length(data)/k;
sorted = sort(data);
X1 = sorted(1 : section);
X2 = sorted(section+1 : section*2);
X = [X1; X2];
mu = [mean(X1); mean(X2)];
sigma = ones(1, k) * std(X);
printInit(mu, sigma, m, k);
pause


x = -10:.1:10;
y1 = gauss(x, mu(1), sigma(1));
y2 = gauss(x, mu(2), sigma(2));
plot(x, y1, 'b-');
hold on;
plot(x, y2, 'r-');
plot(X1, zeros(size(X1)), 'bx');
plot(X2, zeros(size(X2)), 'rx');
set(gcf,'color','white')
pause;



%% Expectation, Maximization
W = zeros(m, k); % probability that each data point belongs to each cluster.
for iter = 1:1000    
    fprintf('Iteration %d\n', iter); 
    pdf = zeros(m, k);% pdf value for each data point for each cluster.
    for j = 1 : k
        pdf(:, j) = gauss(X, mu(j), sigma(j));
    end    
    % Dividing the pdf by the sum of pdf for each cluster.
    W = bsxfun(@rdivide, pdf, sum(pdf, 2));
       
    prevMu = mu;

    for j = 1 : k        
        mu(j) = weightAvg(W(:, j), X);
        sigma(j) = sqrt( weightAvg(W(:, j), (X - mu(j)).^2) ); 
    end

    if (mu == prevMu)
        break;
    end   
    if (iter == 5)
        printPlot(mu, sigma, 'c-', k);
        pause
    elseif (iter == 10)
        printPlot(mu, sigma, 'g-', k);
        pause
    end 
end
printPlot(mu, sigma, 'k-', k);

