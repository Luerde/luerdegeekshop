console.log('Luerde GeekShop JS carregado');

document.addEventListener('DOMContentLoaded', () => {
  // Verifica se estamos na página inicial para carregar os destaques e promoções
  const featuredGrid = document.getElementById('featured-products-grid');
  const saleGrid = document.getElementById('sale-products-grid');

  if (featuredGrid) {
    loadFeaturedProducts();
  }

  if (saleGrid) {
    loadSaleProducts();
  }

  // Verifica se estamos na página de produtos para carregar o catálogo completo
  const allProductsGrid = document.getElementById('products-grid');
  if (allProductsGrid) {
    loadAllProducts();
  }

  // Verifica se estamos na página do carrinho
  const cartItemsContainer = document.getElementById('cart-items');
  if (cartItemsContainer) {
    loadCartItems();

    // Adiciona listener para os botões de remover e de alterar quantidade
    cartItemsContainer.addEventListener('click', (event) => {
        const target = event.target.closest('button');
        if (!target) return;

        const productId = target.dataset.productId;
        let cart = JSON.parse(localStorage.getItem('cart')) || [];
        const productIndex = cart.findIndex(item => item.id == productId);

        if (target.classList.contains('remove-from-cart-btn')) {
            if (productIndex !== -1) {
                cart.splice(productIndex, 1);
            }
        } else if (target.classList.contains('quantity-change-btn')) {
            const change = parseInt(target.dataset.change, 10);
            if (productIndex !== -1) {
                cart[productIndex].quantity += change;
                if (cart[productIndex].quantity <= 0) {
                    cart.splice(productIndex, 1);
                }
            }
        }

        localStorage.setItem('cart', JSON.stringify(cart));
        loadCartItems(); // Recarrega a visualização do carrinho
    });

    cartItemsContainer.addEventListener('change', (event) => {
        const target = event.target;
        if (target.classList.contains('cart-quantity-input')) {
            const productId = target.dataset.productId;
            const newQuantity = parseInt(target.value, 10);
            let cart = JSON.parse(localStorage.getItem('cart')) || [];
            const productIndex = cart.findIndex(item => item.id == productId);

            if (productIndex !== -1) {
                if (newQuantity > 0) {
                    cart[productIndex].quantity = newQuantity;
                } else {
                    cart.splice(productIndex, 1);
                }
            }
            localStorage.setItem('cart', JSON.stringify(cart));
            loadCartItems();
        }
    });
  }

  // Adiciona o listener para os botões "Adicionar ao Carrinho"
  document.body.addEventListener('click', handleAddToCartClick);

  document.body.addEventListener('click', (event) => {
    const target = event.target.closest('.product-quantity-change-btn');
    if (!target) return;

    const productId = target.dataset.productId;
    const change = parseInt(target.dataset.change, 10);
    const quantityInput = document.querySelector(`.quantity-input[data-product-id="${productId}"]`);
    
    if (quantityInput) {
        let currentValue = parseInt(quantityInput.value, 10);
        currentValue += change;
        if (currentValue < 1) {
            currentValue = 1;
        }
        quantityInput.value = currentValue;
    }
  });

  // Verifica se estamos na página de login para carregar a lógica de autenticação
  // const loginForm = document.getElementById('login-form');
  // if (loginForm) {
  //   setupAuthForms();
  // }

  if (window.location.pathname === '/thankyou') {
    clearCart();
  }
});

function clearCart() {
  localStorage.removeItem('cart');
  console.log('Carrinho limpo!');
}

function loadFeaturedProducts() {
  const grid = document.getElementById('featured-products-grid');
  grid.innerHTML = '<p class="col-span-full text-center">Carregando destaques...</p>';

  fetch('/api/products/featured')
    .then(response => response.json())
    .then(products => {
      grid.innerHTML = ''; // Limpa a mensagem de "carregando"
      if (products.length === 0) {
        grid.innerHTML = '<p class="col-span-full text-center text-gray-400">Nenhum produto em destaque no momento.</p>';
        return;
      }
      products.forEach(product => {
        const productCard = `
          <div class="bg-gray-800 rounded-lg p-4 flex flex-col items-center text-center">
            <a href="/produtos/${product.id}">
              <img src="${product.imageUrl || 'https://via.placeholder.com/150'}" alt="${product.name}" class="rounded mb-2 h-40 w-40 object-cover block mx-auto">
              <h4 class="font-bold">${product.name}</h4>
            </a>
            <p class="text-purple-400 font-semibold">R$ ${product.price.toFixed(2).replace('.', ',')}</p>
            <div class="mt-2 flex items-center justify-center">
                <button class="product-quantity-change-btn bg-gray-700 hover:bg-gray-600 px-2 py-1 rounded-l" data-product-id="${product.id}" data-change="-1">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 12H4" />
                    </svg>
                </button>
                <input type="number" value="1" min="1" class="quantity-input bg-gray-700 text-white w-12 text-center" data-product-id="${product.id}">
                <button class="product-quantity-change-btn bg-gray-700 hover:bg-gray-600 px-2 py-1 rounded-r" data-product-id="${product.id}" data-change="1">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
                    </svg>
                </button>
            </div>
            <button class="add-to-cart-btn mt-2 bg-purple-600 hover:bg-purple-700 px-4 py-2 rounded w-full" data-product-id="${product.id}">Adicionar ao Carrinho</button>
          </div>
        `;
        grid.innerHTML += productCard;
      });
    });
}

function loadSaleProducts() {
  const grid = document.getElementById('sale-products-grid');
  grid.innerHTML = '<p class="col-span-full text-center">Carregando promoções...</p>';

  fetch('/api/products/sale')
    .then(response => response.json())
    .then(products => {
      grid.innerHTML = ''; // Limpa a mensagem de "carregando"
      if (products.length === 0) {
        grid.innerHTML = '<p class="col-span-full text-center text-gray-400">Nenhuma promoção ativa no momento.</p>';
        return;
      }
      products.forEach(product => {
        const productCard = `
          <div class="bg-gray-800 rounded-lg p-4 flex flex-col items-center text-center border-2 border-yellow-400">
            <a href="/produtos/${product.id}">
              <img src="${product.imageUrl || 'https://via.placeholder.com/150'}" alt="${product.name}" class="rounded mb-2 h-40 w-40 object-cover block mx-auto">
              <h4 class="font-bold">${product.name}</h4>
            </a>
            <p class="text-gray-400 line-through">De R$ ${product.price.toFixed(2).replace('.', ',')}</p>
            <p class="text-yellow-400 font-bold text-lg">Por R$ ${product.salePrice.toFixed(2).replace('.', ',')}</p>
            <div class="mt-2 flex items-center justify-center">
                <button class="product-quantity-change-btn bg-gray-700 hover:bg-gray-600 px-2 py-1 rounded-l" data-product-id="${product.id}" data-change="-1">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 12H4" />
                    </svg>
                </button>
                <input type="number" value="1" min="1" class="quantity-input bg-gray-700 text-white w-12 text-center" data-product-id="${product.id}">
                <button class="product-quantity-change-btn bg-gray-700 hover:bg-gray-600 px-2 py-1 rounded-r" data-product-id="${product.id}" data-change="1">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
                    </svg>
                </button>
            </div>
            <button class="add-to-cart-btn mt-2 bg-yellow-500 hover:bg-yellow-600 text-gray-900 font-bold px-4 py-2 rounded w-full" data-product-id="${product.id}">Aproveitar!</button>
          </div>
        `;
        grid.innerHTML += productCard;
      });
    })
    .catch(error => {
        grid.innerHTML = '<p class="col-span-full text-center text-red-400">Erro ao carregar promoções.</p>';
        console.error('Error loading sale products:', error);
    });
}

function loadAllProducts() {
  const grid = document.getElementById('products-grid');
  grid.innerHTML = '<p class="col-span-full text-center">Carregando catálogo...</p>';

  fetch('/api/products')
    .then(response => response.json())
    .then(products => {
      grid.innerHTML = ''; // Limpa a mensagem de "carregando"
      if (products.length === 0) {
        grid.innerHTML = '<p class="col-span-full text-center text-gray-400">Nenhum produto encontrado no catálogo.</p>';
        return;
      }
      products.forEach(product => {
        // Define o preço a ser exibido (preço normal ou promocional)
        const priceHtml = product.onSale 
          ? `<p class="text-gray-400 line-through">De R$ ${product.price.toFixed(2).replace('.', ',')}</p>
             <p class="text-yellow-400 font-bold text-lg">Por R$ ${product.salePrice.toFixed(2).replace('.', ',')}</p>`
          : `<p class="text-purple-400 font-semibold text-lg">R$ ${product.price.toFixed(2).replace('.', ',')}</p>`;

        const productCard = `
          <div class="bg-gray-800 rounded-lg p-4 flex flex-col items-center text-center">
            <a href="/produtos/${product.id}">
              <img src="${product.imageUrl || 'https://via.placeholder.com/150'}" alt="${product.name}" class="rounded mb-2 h-48 w-48 object-cover block mx-auto">
              <h4 class="font-bold mt-2">${product.name}</h4>
            </a>
            <div class="mt-2">${priceHtml}</div>
            <div class="mt-4 flex items-center justify-center">
                <button class="product-quantity-change-btn bg-gray-700 hover:bg-gray-600 px-2 py-1 rounded-l" data-product-id="${product.id}" data-change="-1">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 12H4" />
                    </svg>
                </button>
                <input type="number" value="1" min="1" class="quantity-input bg-gray-700 text-white w-12 text-center" data-product-id="${product.id}">
                <button class="product-quantity-change-btn bg-gray-700 hover:bg-gray-600 px-2 py-1 rounded-r" data-product-id="${product.id}" data-change="1">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
                    </svg>
                </button>
            </div>
            <button class="add-to-cart-btn mt-4 bg-purple-600 hover:bg-purple-700 px-4 py-2 rounded w-full" data-product-id="${product.id}">Adicionar ao Carrinho</button>
          </div>`;
        grid.innerHTML += productCard;
      });
    })
    .catch(error => {
        grid.innerHTML = '<p class="col-span-full text-center text-red-400">Erro ao carregar o catálogo. Tente novamente mais tarde.</p>';
        console.error('Error loading all products:', error);
    });
}

// --- LÓGICA DO CARRINHO ---

async function handleAddToCartClick(event) {
    if (event.target.classList.contains('add-to-cart-btn')) {
        event.preventDefault(); // Impede a navegação
        const button = event.target;
        const productId = button.dataset.productId;
        
        const quantityInput = document.querySelector(`.quantity-input[data-product-id="${productId}"]`);
        const quantity = quantityInput ? parseInt(quantityInput.value, 10) : 1;

        if (quantity <= 0) {
            showNotification('A quantidade deve ser pelo menos 1.', true);
            return;
        }

        // Pega o carrinho atual do localStorage ou cria um novo
        let cart = JSON.parse(localStorage.getItem('cart')) || [];
        // Verifica se o produto já está no carrinho
        const existingProduct = cart.find(item => item.id == productId);

        if (existingProduct) {
            existingProduct.quantity += quantity;
            showNotification(`${quantity} unidade(s) de ${existingProduct.name} adicionada(s)!`);
        } else {
            try {
                // Se não estiver, busca os detalhes do produto na nova API /api/products/{id}
                const response = await fetch(`/api/products/${productId}`);
                if (!response.ok) throw new Error('Produto não encontrado!');
                
                const productToAdd = await response.json();
                cart.push({ ...productToAdd, quantity: quantity });
                showNotification(`${quantity} unidade(s) de ${productToAdd.name} foi/foram adicionada(s) ao carrinho!`);

            } catch (error) {
                console.error("Erro ao adicionar produto:", error);
                showNotification("Não foi possível adicionar o produto ao carrinho.", true);
            }
        }

        // Salva o carrinho atualizado no localStorage
        localStorage.setItem('cart', JSON.stringify(cart));
    }
}

function loadCartItems() {
    const cartItemsContainer = document.getElementById('cart-items');
    const cartTotalContainer = document.getElementById('cart-total');
    const checkoutButton = document.getElementById('checkout-button');

    let cart = JSON.parse(localStorage.getItem('cart')) || [];

    cartItemsContainer.innerHTML = ''; // Limpa a lista

    if (cart.length === 0) {
        cartItemsContainer.innerHTML = '<p class="text-gray-400">Seu carrinho está vazio.</p>';
        cartTotalContainer.classList.add('hidden');
        checkoutButton.classList.add('hidden');
        return;
    }

    let subtotal = 0;

    cart.forEach(item => {
        const itemPrice = item.onSale ? item.salePrice : item.price;
        subtotal += itemPrice * item.quantity;

        const cartItemHTML = `
            <div class="bg-gray-800 p-4 rounded-lg flex items-center justify-between">
                <div class="flex items-center gap-4">
                    <img src="${item.imageUrl || 'https://via.placeholder.com/80'}" alt="${item.name}" class="w-20 h-20 rounded object-cover">
                    <div>
                        <h4 class="font-bold">${item.name}</h4>
                        <p class="text-purple-400">R$ ${itemPrice.toFixed(2).replace('.', ',')}</p>
                        <div class="flex items-center mt-2">
                            <button class="quantity-change-btn bg-gray-700 hover:bg-gray-600 px-2 py-1 rounded-l" data-product-id="${item.id}" data-change="-1">
                                <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 12H4" />
                                </svg>
                            </button>
                            <input type="number" value="${item.quantity}" min="1" class="cart-quantity-input bg-gray-700 text-white w-12 text-center" data-product-id="${item.id}">
                            <button class="quantity-change-btn bg-gray-700 hover:bg-gray-600 px-2 py-1 rounded-r" data-product-id="${item.id}" data-change="1">
                                <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
                                </svg>
                            </button>
                        </div>
                    </div>
                </div>
                <button class="remove-from-cart-btn text-red-500 hover:text-red-400" data-product-id="${item.id}">Remover</button>
            </div>
        `;
        cartItemsContainer.innerHTML += cartItemHTML;
    });

    // Atualiza o subtotal
    cartTotalContainer.querySelector('#subtotal-price').textContent = `R$ ${subtotal.toFixed(2).replace('.', ',')}`;
    cartTotalContainer.classList.remove('hidden');
    checkoutButton.classList.remove('hidden');
}

// --- LÓGICA DE AUTENTICAÇÃO ---

// --- LÓGICA DE AUTENTICAÇÃO ---

function showNotification(message, isError = false) {
    const notificationId = 'custom-notification-' + Date.now();
    const notification = document.createElement('div');
    notification.id = notificationId;
    notification.className = 'fixed bottom-4 right-4 p-4 rounded-lg shadow-lg text-white z-50 transition-all duration-300 ease-in-out transform translate-x-full opacity-0';
    
    if (isError) {
        notification.classList.add('bg-red-600');
    } else {
        notification.classList.add('bg-green-600');
    }
    
    notification.textContent = message;
    document.body.appendChild(notification);

    // Animate in
    setTimeout(() => {
        notification.classList.remove('translate-x-full', 'opacity-0');
        notification.classList.add('translate-x-0', 'opacity-100');
    }, 100); // Small delay to allow CSS transition to apply

    // Animate out and remove
    setTimeout(() => {
        notification.classList.remove('translate-x-0', 'opacity-100');
        notification.classList.add('translate-x-full', 'opacity-0');
        notification.addEventListener('transitionend', () => {
            notification.remove();
        });
    }, 3000); // Notification disappears after 3 seconds
}

// function setupAuthForms() {
//     const loginContainer = document.getElementById('login-container');
//     const registerContainer = document.getElementById('register-container');
//     const showRegisterBtn = document.getElementById('show-register-btn');
//     const showLoginBtn = document.getElementById('show-login-btn');

//     const loginForm = document.getElementById('login-form');
//     const registerForm = document.getElementById('register-form');

//     // Alternar para o formulário de registro
//     showRegisterBtn.addEventListener('click', () => {
//         loginContainer.classList.add('hidden');
//         registerContainer.classList.remove('hidden');
//     });

//     // Alternar para o formulário de login
//     showLoginBtn.addEventListener('click', () => {
//         registerContainer.classList.add('hidden');
//         loginContainer.classList.remove('hidden');
//     });

//     // Lidar com o envio do formulário de login
//     loginForm.addEventListener('submit', async (event) => {
//         event.preventDefault();
//         const email = document.getElementById('login-email').value;
//         const password = document.getElementById('login-password').value;
//         const messageDiv = document.getElementById('login-message');

//         try {
//             const response = await fetch('/api/auth/login', {
//                 method: 'POST',
//                 headers: { 'Content-Type': 'application/json' },
//                 body: JSON.stringify({ email, password })
//             });

//             const resultText = await response.text();

//             if (response.ok) {
//                 messageDiv.textContent = resultText;
//                 messageDiv.className = 'p-2 rounded text-center bg-green-500 text-white';
//                 // Redireciona para a página inicial após 2 segundos
//                 setTimeout(() => window.location.href = '/', 2000);
//             } else {
//                 messageDiv.textContent = resultText;
//                 messageDiv.className = 'p-2 rounded text-center bg-red-500 text-white';
//             }
//             messageDiv.classList.remove('hidden');

//         } catch (error) {
//             console.error('Erro no login:', error);
//             messageDiv.textContent = 'Erro de conexão. Tente novamente.';
//             messageDiv.className = 'p-2 rounded text-center bg-red-500 text-white';
//             messageDiv.classList.remove('hidden');
//         }
//     });

//     // Lidar com o envio do formulário de registro
//     registerForm.addEventListener('submit', async (event) => {
//         event.preventDefault();
//         const username = document.getElementById('register-username').value;
//         const email = document.getElementById('register-email').value;
//         const password = document.getElementById('register-password').value;
//         const messageDiv = document.getElementById('register-message');

//         try {
//             const response = await fetch('/api/auth/register', {
//                 method: 'POST',
//                 headers: { 'Content-Type': 'application/json' },
//                 body: JSON.stringify({ username, email, password })
//             });

//             const resultText = await response.text();

//             if (response.ok) {
//                 messageDiv.textContent = resultText + " Você já pode fazer o login.";
//                 messageDiv.className = 'p-2 rounded text-center bg-green-500 text-white';
//                 registerForm.reset();
//             } else {
//                 messageDiv.textContent = resultText;
//                 messageDiv.className = 'p-2 rounded text-center bg-red-500 text-white';
//             }
//             messageDiv.classList.remove('hidden');

//         } catch (error) {
//             console.error('Erro no registro:', error);
//             messageDiv.textContent = 'Erro de conexão. Tente novamente.';
//             messageDiv.className = 'p-2 rounded text-center bg-red-500 text-white';
//             messageDiv.classList.remove('hidden');
//         }
//     });
// }
