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

// Client-side pagination (only for pages with .post-box elements)
document.addEventListener("DOMContentLoaded", () => {

    const posts = document.querySelectorAll(".post-box");

    // Exit if this page doesn't use client-side pagination.
    // This allows Spring Boot/Thymeleaf pagination to work normally.
    if (posts.length === 0) {
        return;
    }

    const perPage = 3;
    let currentPage = 1;

    // Only page number buttons (exclude Prev/Next)
    const pageButtons = document.querySelectorAll(".page-btn:not(.prev):not(.next)");
    const prevBtn = document.querySelector(".page-btn.prev");
    const nextBtn = document.querySelector(".page-btn.next");

    function showPage(page) {

        posts.forEach((post, index) => {
            post.style.display =
                index >= (page - 1) * perPage &&
                index < page * perPage
                    ? "block"
                    : "none";
        });

        pageButtons.forEach(btn => {
            btn.classList.toggle("active", Number(btn.textContent) === page);
        });

        if (prevBtn) {
            prevBtn.style.visibility = page === 1 ? "hidden" : "visible";
        }

        if (nextBtn) {
            nextBtn.style.visibility =
                page === Math.ceil(posts.length / perPage)
                    ? "hidden"
                    : "visible";
        }
    }

    showPage(currentPage);

    if (prevBtn) {
        prevBtn.addEventListener("click", function (e) {
            e.preventDefault();
            if (currentPage > 1) {
                currentPage--;
                showPage(currentPage);
            }
        });
    }

    if (nextBtn) {
        nextBtn.addEventListener("click", function (e) {
            e.preventDefault();
            if (currentPage < Math.ceil(posts.length / perPage)) {
                currentPage++;
                showPage(currentPage);
            }
        });
    }

    pageButtons.forEach(btn => {
        btn.addEventListener("click", function (e) {
            e.preventDefault();
            currentPage = Number(this.textContent);
            showPage(currentPage);
        });
    });

});
