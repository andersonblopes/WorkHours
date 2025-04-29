function changeLanguage(select) {
    const lang = select.value;
    const url = new URL(window.location.href);
    url.searchParams.set('lang', lang);
    window.location.href = url.toString();
}
