# returns 2d array of all contents in file
def read_file(file_path):
    data = []
    file = open(file_path, 'r')
    for line in file:
        stripped_line = line.strip()
        if ": " in stripped_line:
            type, stripped_line = stripped_line.split(": ", 1)
        data.append(stripped_line)

    return data

def load_dict(data_type, dictionary, data):
    key = data.pop(0)
    authors = data.pop(0)
    title = data.pop(0)
    author_string = make_author_string(authors)

    common_attributes = f"{author_string}, {title}"
    unique_attributes = ""
    if data_type == "Book":
        unique_attributes = load_book_attributes(data)
    elif data_type == "Journal":
        unique_attributes = load_journal_attributes(data)
    elif data_type == "Conference":
        unique_attributes = load_conference_attributes(data)
    
    dictionary[key] = f"{common_attributes}, {unique_attributes}"
    

def load_book_attributes(book_data):
    publisher = book_data.pop(0)
    date = book_data.pop(0)

    return f"{publisher}, {date}."

# Dictionary of the journal data
def load_journal_attributes(journal_data):
    journal = journal_data.pop(0)
    publisher = journal_data.pop(0)
    date = journal_data.pop(0)
    volume = journal_data.pop(0)
    number = journal_data.pop(0)

    return f"{journal}, {publisher}:{volume}({number}), {date}."
    

def load_conference_attributes(conference_data):
    conference = conference_data.pop(0)
    date = conference_data.pop(0)
    location = conference_data.pop(0)
    pages = conference_data.pop(0)

    return f"in Proceedings of {conference}, {location}, {date}, {pages}."
    
def make_author_string(authors):
    author_list = authors.split(", ")
    names_list = []
    for author in author_list:
        first, last = author.split(" ")
        names_list.append(last)
        names_list.append(first)
        
    author_string = ""
    while names_list:
        last_name = names_list.pop(0)
        first_name = names_list.pop(0)
        if author_string:
            author_string = f"{author_string}, {last_name} {first_name}"
        else:
            author_string = f"{last_name}, {first_name}"
    return author_string

def print_data(library_dictionary, key):
    print(f"{key}\t{library_dictionary[key]}")
    

def main():
    #todo
    # fix author parsing
    # comments/ documentation
    #Author: Bo Brinkman, Valerie Cross, and Lucasz Opyrchal
    #Author: Ken Abernethy, John Kelly, Ann Sobel, James Kiper
    #Abernethy2000        Abernethy, Ken, John Kelly, Ann Sobel, James Kiper, Technology transfer issues for formal methods of software specification, in Proceedings of Thirteenth Conference on Software Engineering Education & Training, Austin, Texas, March 6-8, 2000, 23-31.

    library_data = read_file("Data/Step3Data.txt")
    library_dictionary = {}
    
    while library_data:
        data_type = library_data.pop(0)
        load_dict(data_type, library_dictionary, library_data)

    print_data(library_dictionary, input("Enter key for data: "))

main()