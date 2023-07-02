import requests
import bs4

MAIN_URL_SHORT = 'https://www.lidl.es'
MAIN_URL = 'https://www.lidl.es/es/search?id=7300d223-768b-4d3d-b5b8-0d9caa1f7bc0&q=moda&assortment=ES&locale=es_ES&version=v2.0.0&variant=default&idsOnly=true&category=Moda&page='
MAX_PAGES = 28
DOWNLOAD_PATH = r'C:\Users\Juanma\Desktop\repos\WasteClassification\Scrapper\images'.replace('\\', '/')

def get_products(page):
    # Get all src attributes from images inside the div with class 'product-grid-box-tile'
    response = requests.get(MAIN_URL+str(page))
    soup = bs4.BeautifulSoup(response.text, 'html.parser')
    products = soup.select('.product-grid-box-tile img')
    return list(set([MAIN_URL_SHORT + product['src'] for product in products if 'media' in product['src']]))

for page in range(1, MAX_PAGES):
    for image in get_products(page):
        # Download image
        response = requests.get(image)
        with open(DOWNLOAD_PATH + '\\' + image.split('/')[-1], 'wb') as f:
            f.write(response.content)

        print('Descargado: ' + image.split('/')[-1])
