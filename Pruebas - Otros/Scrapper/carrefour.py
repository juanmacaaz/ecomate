import requests
import bs4
import time

SHORT_URL = 'https://www.carrefour.es'
MAIN_URL = 'https://www.carrefour.es/electrodomesticos/climatizacion/cat5980074/c'
MAX_PAGES = 1008
DOWNLOAD_PATH = r'C:\Users\Juanma\Desktop\repos\WasteClassification\Scrapper\images'.replace('\\', '/')

def get_categories():
    response = requests.get(MAIN_URL)
    soup = bs4.BeautifulSoup(response.text, 'html.parser')
    categories = soup.select('.nav-first-level-categories__list-element')
    lista = list(set([category['href'] for category in categories if not 'https' in category['href']]))
    return lista

def get_products(main_url, page):
    # Get all src attributes from images inside the div with class 'media__image'
    print(SHORT_URL + main_url + '?offset=' + str(page))
    response = requests.get(SHORT_URL + main_url + '?offset=' + str(page))
    soup = bs4.BeautifulSoup(response.text, 'html.parser')
    products = soup.select('.media__image')
    return list(set([product['src'] for product in products]))

def get_total_pages(main_url):
    response = requests.get(SHORT_URL + main_url)
    soup = bs4.BeautifulSoup(response.text, 'html.parser')
    total = soup.select('.pagination__results-item')[-1].text
    return int(total)

for url in get_categories():
    category = url
    total = get_total_pages(category)
    for page in range(0, total, 24):
        for image in get_products(category, page):
            # Download image
            response = requests.get(image.replace('hd_350x_', 'hd_1500x_'))
            time_stamp = str(time.time()).replace('.', '')
            with open(DOWNLOAD_PATH + '\\' + image.split('/')[-1].replace('imagenGrande1', time_stamp), 'wb') as f:
                f.write(response.content)

            print('Descargado: ' + image.split('/')[-1])