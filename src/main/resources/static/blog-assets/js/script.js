// Filter JS
$(document).ready(function () {
    $('.filter-item').click(function () {
        const value = $(this).attr('data-filter');
        if (value == 'all') {
            $('.post-box').show('1000');
        } else {
            $('.post-box').not('.' + value).hide('1000');
            $('.post-box').filter('.' + value).show('1000');
        }
    });

    // Fix active class (use active-filter instead of active-item)
    $('.filter-item').click(function () {
        $(this).addClass('active-filter').siblings().removeClass('active-filter');
    });
});

// Header Background Change on Scroll
let header = document.querySelector('.header');
window.addEventListener('scroll', function () {
    header.classList.toggle('shadow', window.scrollY > 0);
});

// Hamburger toggle
function toggleMenu() {
    document.querySelector(".nav").classList.toggle("active");
}

// Pagination logic
document.addEventListener("DOMContentLoaded", () => {
    const posts = document.querySelectorAll(".post-box");
    const perPage = 3; // number of posts per page
    let currentPage = 1;

    function showPage(page) {
        posts.forEach((post, index) => {
            post.style.display =
                index >= (page - 1) * perPage && index < page * perPage ? "block" : "none";
        });

        document.querySelectorAll(".page-btn").forEach(btn => {
            btn.classList.remove("active");
            if (btn.textContent == page) btn.classList.add("active");
        });
    }

    // Initial load
    showPage(currentPage);

    // Handle pagination clicks
    document.querySelectorAll(".page-btn").forEach(btn => {
        btn.addEventListener("click", e => {
            e.preventDefault();
            if (btn.classList.contains("prev") && currentPage > 1) {
                currentPage--;
            } else if (btn.classList.contains("next") && currentPage < Math.ceil(posts.length / perPage)) {
                currentPage++;
            } else if (!btn.classList.contains("prev") && !btn.classList.contains("next")) {
                currentPage = parseInt(btn.textContent);
            }
            showPage(currentPage);
        });
    });
});
