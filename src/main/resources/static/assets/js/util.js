export function debounce(callback, wait) {
    let timer;
    return (...value) => {
        clearTimeout(timer);

        timer = setTimeout(() => {
            callback(...value);
        }, wait);
    }
}
