function printInit(mu, sigma, m, k)  
    fprintf('Clauses %d\n', k);
    fprintf('Data length %d\n', m);
    for i = 1 : k
        fprintf('Mean%d %d\n', i, mu(i));
        fprintf('Sigma%d %d\n', i, sigma(i));
    end 
    fprintf('End of Init ...  Press enter to continiue\n\n');
end