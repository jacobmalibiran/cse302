# Returns array of all contents in the file
def read_file(file_path):
    data = []
    file = open(file_path, 'r')
    for line in file:
        stripped_line = line.strip()
        # Data is formatted like "Author: James Kiper"
        # Gets data after colon
        if ": " in stripped_line:
            type, stripped_line = stripped_line.split(": ", 1)
        data.append(stripped_line)

    return data

# Returns dictionary of all keys and data
def load_dict(data_type, dictionary, data):
    # Gets common data for all data types
    key = data.pop(0)
    authors = data.pop(0)
    title = data.pop(0)
    author_string = make_author_string(authors)

    # Author and title are common to all file types
    # Unique_attributes handles the different values
    common_attributes = f"{author_string}, {title}"
    unique_attributes = ""

    # Loads data differently depending on type
    if data_type == "Book":
        unique_attributes = load_book_attributes(data)
    elif data_type == "Journal":
        unique_attributes = load_journal_attributes(data)
    elif data_type == "Conference":
        unique_attributes = load_conference_attributes(data)
    
    dictionary[key] = f"{common_attributes}, {unique_attributes}"
    
# Returns unique attributes for book data
def load_book_attributes(book_data):
    publisher = book_data.pop(0)
    date = book_data.pop(0)

    return f"{publisher}, {date}."

# Returns formatted unique attributes of journal data
def load_journal_attributes(journal_data):
    journal = journal_data.pop(0)
    publisher = journal_data.pop(0)
    date = journal_data.pop(0)
    volume = journal_data.pop(0)
    number = journal_data.pop(0)

    return f"{journal}, {publisher}:{volume}({number}), {date}."
    
# Finds and returns conference specific data as a string
def load_conference_attributes(conference_data):
    conference = conference_data.pop(0)
    date = conference_data.pop(0)
    location = conference_data.pop(0)
    pages = conference_data.pop(0)

    return f"in Proceedings of {conference}, {location}, {date}, {pages}."
    
# Extracts and formats authors from data, returning as a string
def make_author_string(authors):
    author_list = authors.split(", ")
    names_list = []
    
    for author in author_list:
        
        # If the last author has "and" such as ", and Lucasz Opyrchal", we remove it
        author_name = author.split(" ")
        # Sometimes, the last author in a list will have "and" preceeding their name
        if author_name[0] == "and":
            author_name = author[1:]
        
        first_name_idx = 0
        last_name_idx = 1

        names_list.append(author_name[first_name_idx])
        names_list.append(author_name[last_name_idx])
        
    author_string = ""
    while names_list:
        last_name = names_list.pop(0)
        first_name = names_list.pop(0)

        # The first author in a list is formatted differently
        if author_string:
            author_string = f"{author_string}, {last_name} {first_name}"
        else:
            author_string = f"{first_name}, {last_name}"
    return author_string

# Prints formatted data based on a user-input key
def print_data(library_dictionary, key):
    # Sample output shows 8 spaces as opposed to a tab.
    print(f"{key}        {library_dictionary[key]}")
    

# Loads library data from a file into a dictionary, printing an entry based on the user input key
def main():
    library_data = read_file("Data/Step3Data.txt")
    library_dictionary = {}
    
    # While there is still data in file, keep loading it in the dictionary
    while library_data:
        # Gets data type if it is a book, journal, or conference
        data_type = library_data.pop(0)
        load_dict(data_type, library_dictionary, library_data)

    print_data(library_dictionary, input("Enter key for data: "))

main()