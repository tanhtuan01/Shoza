let productTable = new DataTable('#productTable');

$('#productTable').DataTable().destroy();

$(document).ready(function() {
    $('#productTable').DataTable({
        "language": {
            "sEmptyTable": "Không có dữ liệu",
            "sInfo": "Hiển thị từ _START_ đến _END_ (tổng: _TOTAL_) ",
            "lengthMenu": "Hiển thị _MENU_ sản phẩm",
            "emptyTable": "Không có sản phẩm phù hợp",
            "zeroRecords": "Không tìm thấy sản phẩm phù hợp",
            "infoEmpty": "0 / ",
            "infoFiltered": "_MAX_",
            "search": "Tìm kiếm",
            "paginate": {
                "previous": "Trước",
                "next": "Tiếp"
            },
        },
        lengthMenu: [10, 20, 30, 40],
        columnDefs: [
            { "orderable": false, "targets": [1,2,8,9,10] } // chỉ mục muốn tắt sorting
        ],
        initComplete: function() {
            this.api()
                .columns()
                .every(function() {
                    var column = this;
                    var title = column.header().textContent.trim();
                    var footer = $(column.footer());

                    if (footer && footer.length > 0 && column.index() != 0 && column.index() != 1 && column.index() != 4 && column.index() !=5) {
                        var input = document.createElement('input');
                        input.placeholder = 'Tìm kiếm ' + title;

                        $(input).appendTo(footer.empty())
                            .on('keyup change', function() {
                                if (column.search() !== this.value) {
                                    column.search(this.value).draw();
                                }
                            });
                    }
                });
        }
    });
});